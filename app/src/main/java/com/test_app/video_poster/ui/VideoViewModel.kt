package com.test_app.video_poster.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.test_app.video_poster.App.Companion.appComponent
import com.test_app.video_poster.data.VideoApi
import com.test_app.video_poster.data.VideoData
import com.test_app.video_poster.util.State
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class VideoViewModel: ViewModel() {
    private val _videoLiveData = MutableLiveData<State>()
    val videoLiveData: LiveData<State> = _videoLiveData

    private val disposable: CompositeDisposable = CompositeDisposable()

    init {
        getVideoData(appComponent.getVideoApi())
    }

    fun getVideoData(videoApi: VideoApi) {
        disposable.add(
            videoApi.getVideoFromRemote()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ data ->
                    _videoLiveData.value = State.Success(data)
                }, {
                    _videoLiveData.value = State.Error(it.message.orEmpty())
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}