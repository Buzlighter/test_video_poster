package com.test_app.video_poster.util

import com.test_app.video_poster.data.VideoData

interface OnPosterClickListener {
    fun onPosterClick(videoData: VideoData, position: Int)
}