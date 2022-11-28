package com.test_app.video_poster.data

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface VideoApi {

    @GET("api/backgrounds/")
    fun getVideoFromRemote(
        @Query("group") groupName: String = "video",
        @Query("category_id") id: String = "1"
    ): Single<ArrayList<VideoData>>
}