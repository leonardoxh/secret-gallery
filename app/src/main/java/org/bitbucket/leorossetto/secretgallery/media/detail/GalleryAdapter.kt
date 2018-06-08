package org.bitbucket.leorossetto.secretgallery.media.detail

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import org.bitbucket.leorossetto.secretgallery.common.isVideo
import java.io.File

class GalleryAdapter(fm: FragmentManager, private val files: List<File>?): FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment? {
        val file = files?.get(position) ?: return null
        if (file.isVideo()) {
            return VideoMediaDetailFragment.newInstance(file)
        }
        return ImageMediaDetailFragment.newInstance(file)
    }

    override fun getCount(): Int = files?.size ?: 0
}