package com.test_app.video_poster.di

import com.test_app.video_poster.data.VideoApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface AppComponent {

    fun getVideoApi(): VideoApi
}