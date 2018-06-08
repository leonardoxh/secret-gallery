package org.bitbucket.leorossetto.secretgallery.media.select

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import javax.inject.Inject

class MediaSelectViewModelFactory @Inject constructor(private val application: Application):
        ViewModelProvider.AndroidViewModelFactory(application) {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            MediaSelectViewModel(application) as T
}