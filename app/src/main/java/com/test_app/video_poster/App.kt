package com.test_app.video_poster

import android.app.Application
import com.test_app.video_poster.di.AppComponent
import com.test_app.video_poster.di.DaggerAppComponent
import com.test_app.video_poster.di.NetworkModule

class App: Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .networkModule(NetworkModule)
            .build()
    }
}