package com.test_app.video_poster.ui

import android.content.Context
import android.graphics.Color
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.test_app.video_poster.R
import com.test_app.video_poster.data.VideoData
import com.test_app.video_poster.databinding.PosterHolderItemBinding
import com.test_app.video_poster.util.OnPosterClickListener

class PosterHolder(private val binding: PosterHolderItemBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(videoData: VideoData, clickListener: OnPosterClickListener, position: Int, context: Context) {
        binding.apply {
            Picasso.get()
                .load(videoData.smallPosterUrl)
                .resize(250, 250)
                .into(posterImg)

            root.setOnClickListener {
                clickListener.onPosterClick(videoData, position)
            }
        }

    }
}