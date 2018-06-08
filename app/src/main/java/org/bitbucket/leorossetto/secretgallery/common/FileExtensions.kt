package org.bitbucket.leorossetto.secretgallery.common

import android.webkit.MimeTypeMap
import java.io.File

fun File.isVideo(): Boolean {
    val ext = MimeTypeMap.getFileExtensionFromUrl(absolutePath)
    return MimeTypeMap.getSingleton()
            .getMimeTypeFromExtension(ext)?.startsWith("video") == true
}