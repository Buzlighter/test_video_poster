package com.test_app.video_poster.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.test_app.video_poster.R
import com.test_app.video_poster.data.VideoData
import com.test_app.video_poster.databinding.PosterHolderItemBinding
import com.test_app.video_poster.util.OnPosterClickListener

class VideoRecycleAdapter(private val clickListener: OnPosterClickListener,
                          private val context: Context): RecyclerView.Adapter<PosterHolder>() {
    var selectedPos = RecyclerView.NO_POSITION

    private val differCallBack = object: DiffUtil.ItemCallback<VideoData>() {
        override fun areItemsTheSame(oldItem: VideoData, newItem: VideoData): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: VideoData, newItem: VideoData): Boolean {
            return oldItem == newItem
        }
    }

    var differ = AsyncListDiffer(this, differCallBack)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PosterHolder {
        val view = PosterHolderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PosterHolder(view)
    }

    override fun onBindViewHolder(holder: PosterHolder, position: Int) {
        if (holder.itemView.isSelected) {
            selectedPos = position
        }

        if (selectedPos == position) {
            holder.itemView.background =
                AppCompatResources.getDrawable(context, R.drawable.shape_selector_posterlist_enabled)
        } else {
            holder.itemView.background =
                AppCompatResources.getDrawable(context, R.drawable.shape_selector_posterlist_disabled)
        }

        val filmItem = differ.currentList[position]
        holder.bind(filmItem, clickListener, position, context)
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }

    fun setSelectedPosition(position: Int) {
        selectedPos = position
    }
}