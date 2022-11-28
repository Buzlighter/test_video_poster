package com.test_app.video_poster.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.test_app.video_poster.R
import com.test_app.video_poster.data.VideoData
import com.test_app.video_poster.databinding.ActivityMainBinding
import com.test_app.video_poster.util.OnPosterClickListener
import com.test_app.video_poster.util.State


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var videoAdapter: VideoRecycleAdapter

    private val videoViewModel: VideoViewModel by viewModels()
    private var videoList = ArrayList<VideoData>()
    private val listPositionsPoster = mutableListOf(0)

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        videoViewModel.videoLiveData.observe(this) { result ->
            when(result) {
                is State.Success -> {
                    videoList = result.videoList
                    videoAdapter.differ.submitList(videoList)
                    binding.videoView.setVideoPath(result.videoList[0].videoFileUrl)
                    binding.videoView.start()
                }
                is State.Error ->  {
                    Toast.makeText(this, R.string.error, Toast.LENGTH_LONG).show()
                }
            }
        }

        setNewsRecycler()

        binding.apply {
            textBtn.setOnClickListener(textButtonClickListener)
            backBtn.setOnClickListener(backClickListener)
            editText.setOnTouchListener(onEditTextTouchListener)
        }
    }

    private fun setNewsRecycler() {
        binding.posterRecycler.apply {
            videoAdapter = VideoRecycleAdapter(posterClickListener, this@MainActivity)
            adapter = videoAdapter
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            setHasFixedSize(true)
            videoAdapter.setSelectedPosition(0)
        }
    }

    var dx = 0F
    var dy = 0F
    @SuppressLint("ClickableViewAccessibility")
    private val onEditTextTouchListener = View.OnTouchListener { view, event ->
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                dx = view.x - event.rawX
                dy = view.y - event.rawY
            }
            MotionEvent.ACTION_UP -> {
                binding.editText.isEnabled = true
            }
            MotionEvent.ACTION_MOVE -> {
                view.animate()
                    .x(event.rawX + dx)
                    .y(event.rawY + dy)
                    .setDuration(0)
                    .start()
            }
        }
        return@OnTouchListener false
    }

    private val posterClickListener = object: OnPosterClickListener {
        override fun onPosterClick(videoData: VideoData, position: Int) {
            setVideoAndAdapter(position)
            listPositionsPoster.add(position)
            isAfterAdding = true
            Log.v("posterlist", "\n $listPositionsPoster")
        }
    }

    private val textButtonClickListener = View.OnClickListener {
            if (binding.textBtn.isChecked) {
                binding.editText.visibility = View.VISIBLE
            } else {
                binding.editText.visibility = View.INVISIBLE
            }
    }

    var isAfterAdding = false
    private val backClickListener = View.OnClickListener {
        if (listPositionsPoster.size > 1) {
            if (isAfterAdding) {
                listPositionsPoster.remove(listPositionsPoster.last())
            }
            binding.posterRecycler.smoothScrollToPosition(listPositionsPoster.last())
            setVideoAndAdapter(listPositionsPoster.last())
            listPositionsPoster.remove(listPositionsPoster.last())
            Log.v("posterlist", "\n $listPositionsPoster")
        } else {
            setVideoAndAdapter(listPositionsPoster.first())
            binding.posterRecycler.smoothScrollToPosition(listPositionsPoster.first())
        }
        isAfterAdding = false
    }

    private fun setVideoAndAdapter(position: Int) {
        binding.apply {
            videoView.setVideoPath(videoList[position].videoFileUrl)
            videoView.start()
            videoAdapter.setSelectedPosition(position)
            posterRecycler.adapter?.notifyDataSetChanged()
        }
    }
}