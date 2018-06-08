package org.bitbucket.leorossetto.secretgallery.media.select

import android.app.Activity
import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.content.Intent
import android.net.Uri
import android.support.v4.app.ShareCompat
import android.support.v4.content.FileProvider
import android.webkit.MimeTypeMap
import okio.Okio
import org.bitbucket.leorossetto.secretgallery.R
import java.io.File
import java.io.FileInputStream

class MediaSelectViewModel(application: Application): AndroidViewModel(application) {
    fun newImportIntent(): Intent {
        val importIntent = Intent(Intent.ACTION_GET_CONTENT)
        importIntent.apply {
            putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
            putExtra(Intent.CATEGORY_OPENABLE, true)
            putExtra(Intent.EXTRA_MIME_TYPES, arrayOf("image/*", "video/*"))
            type = "*/*"
        }
        return Intent.createChooser(importIntent, getApplication<Application>()
                .getText(R.string.media_import_pick_title))
    }

    fun onActivityResult(data: Intent?) {
        if (data?.data == null) {
            val clipData = data?.clipData
            if (clipData != null) {
                val itemCount = clipData.itemCount
                for (i in 0 until itemCount) {
                    val item = clipData.getItemAt(i)
                    writeFileToInternalStorage(item.uri)
                }
            }
        } else {
            writeFileToInternalStorage(data.data)
        }
    }

    fun startShareIntentWith(activity: Activity, files: Collection<File>) {
        val shareIntentBuilder = ShareCompat.IntentBuilder.from(activity)
        files.forEach { file ->
            shareIntentBuilder.addStream(FileProvider.getUriForFile(activity,
                    "org.bitbucket.leorossetto.secretgallery.fileprovider",
                    file))
        }
        shareIntentBuilder.setType("*/*")
        shareIntentBuilder.startChooser()
    }

    fun removeFiles(files: Collection<File>) {
        files.forEach { file ->
            file.delete()
        }
    }

    private fun writeFileToInternalStorage(data: Uri) {
        val file = newFileForUri(data) ?: return
        val parcelFileDescriptor = getApplication<Application>().contentResolver
                .openFileDescriptor(data, "r")

        FileInputStream(parcelFileDescriptor.fileDescriptor).use { fileInput ->
            Okio.sink(file).use { fileSink ->
                Okio.buffer(fileSink).use { bufferedSink ->
                    bufferedSink.writeAll(Okio.source(fileInput))
                }
            }
        }
    }

    private fun newFileForUri(data: Uri): File? {
        val mimeType = getApplication<Application>().contentResolver.getType(data)
        if (mimeType != null) {
            val extension = MimeTypeMap.getSingleton()
                    .getExtensionFromMimeType(mimeType) ?: return null
            return File(getApplication<Application>().filesDir, "${System.currentTimeMillis()}.$extension")
        }
        return null
    }
}