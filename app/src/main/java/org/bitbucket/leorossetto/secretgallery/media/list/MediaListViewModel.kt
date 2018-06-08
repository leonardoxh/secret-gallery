package org.bitbucket.leorossetto.secretgallery.media.list

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import java.io.File

class MediaListViewModel(application: Application): AndroidViewModel(application) {
    private var mediaListFileObserver: MediaListFileObserver? = null
    val filesLiveData = MutableLiveData<List<File>>()

    fun loadFiles() {
        if (mediaListFileObserver == null) {
            filesLiveData.value = getApplication<Application>().filesDir.listFiles().asList()
            mediaListFileObserver = MediaListFileObserver(getApplication<Application>().filesDir.absolutePath, {
                filesLiveData.postValue(getApplication<Application>().filesDir.listFiles().asList())
            })
            mediaListFileObserver?.startWatching()
        }
    }

    override fun onCleared() {
        mediaListFileObserver?.stopWatching()
        super.onCleared()
    }
}