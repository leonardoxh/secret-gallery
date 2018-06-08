package org.bitbucket.leorossetto.secretgallery.media.list

import android.os.FileObserver

class MediaListFileObserver(path: String, private val observer: () -> Unit): FileObserver(path) {
    override fun onEvent(event: Int, path: String?) {
        if (event == FileObserver.CLOSE_WRITE || event == FileObserver.DELETE) {
            observer.invoke()
        }
    }
}