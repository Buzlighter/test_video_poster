package com.test_app.video_poster.data

import com.google.gson.annotations.SerializedName

data class VideoData(
    @SerializedName("id")
    val id: String,
    @SerializedName("file_url")
    val videoFileUrl: String,
    @SerializedName("poster_url")
    val posterUrl: String,
    @SerializedName("small_poster_url")
    val smallPosterUrl: String
)
