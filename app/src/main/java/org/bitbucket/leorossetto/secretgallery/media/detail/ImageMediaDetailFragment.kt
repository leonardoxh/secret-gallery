package org.bitbucket.leorossetto.secretgallery.media.detail

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_image_media_detail.*
import org.bitbucket.leorossetto.secretgallery.R
import org.bitbucket.leorossetto.secretgallery.common.view.setImageFile
import java.io.File

class ImageMediaDetailFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_image_media_detail, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val file = arguments?.getSerializable(EXTRA_FILE) ?: return
        imageDetailImage.setImageFile(file as File)
    }

    companion object {
        private const val EXTRA_FILE = "extra-file-details"

        fun newInstance(file: File): ImageMediaDetailFragment {
            val fragment = ImageMediaDetailFragment()
            fragment.arguments = Bundle().apply {
                putSerializable(EXTRA_FILE, file)
            }
            return fragment
        }
    }
}