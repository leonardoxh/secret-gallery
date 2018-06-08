package org.bitbucket.leorossetto.secretgallery.common

import android.media.ThumbnailUtils
import android.provider.MediaStore
import android.webkit.MimeTypeMap
import com.squareup.picasso.Picasso
import com.squareup.picasso.Request
import com.squareup.picasso.RequestHandler

class PicassoVideoRequestHandler: RequestHandler() {
    override fun canHandleRequest(request: Request): Boolean {
        val ext = MimeTypeMap.getFileExtensionFromUrl(request.uri.path) ?: return false
        val mimeType = MimeTypeMap.getSingleton().getMimeTypeFromExtension(ext)
        return mimeType?.startsWith("video") == true
    }

    override fun load(request: Request, p1: Int): Result? {
        val thumbNail = ThumbnailUtils.createVideoThumbnail(request.uri.path,
                MediaStore.Images.Thumbnails.MINI_KIND)
        return Result(thumbNail, Picasso.LoadedFrom.DISK)
    }
}