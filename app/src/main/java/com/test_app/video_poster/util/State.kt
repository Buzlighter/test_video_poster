package com.test_app.video_poster.util

import com.test_app.video_poster.data.VideoData

sealed class State {
    data class Success(val videoList: ArrayList<VideoData>): State()
    data class Error(val msg: String): State()
}
