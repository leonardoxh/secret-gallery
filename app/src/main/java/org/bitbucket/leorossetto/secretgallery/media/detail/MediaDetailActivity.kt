package org.bitbucket.leorossetto.secretgallery.media.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager.LayoutParams.FLAG_SECURE
import kotlinx.android.synthetic.main.activity_media_detail.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.bitbucket.leorossetto.secretgallery.R
import org.bitbucket.leorossetto.secretgallery.common.dagger.appComponent
import org.bitbucket.leorossetto.secretgallery.media.list.MediaListViewModel
import java.io.File

class MediaDetailActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(FLAG_SECURE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        val viewModel = ViewModelProviders.of(this, appComponent().mediaListViewModelFactory())
                .get(MediaListViewModel::class.java)

        viewModel.filesLiveData.observe(this, Observer { files ->
            gallery.adapter = GalleryAdapter(supportFragmentManager, files)

            if (intent.hasExtra(EXTRA_SELECTED_FILE)) {
                val selectedFile = intent.getSerializableExtra(EXTRA_SELECTED_FILE)
                val selectedIndex = files?.indexOf(selectedFile) ?: 0
                if (selectedIndex >= 0) {
                    gallery.setCurrentItem(selectedIndex, false)
                }
                intent.removeExtra(EXTRA_SELECTED_FILE)
            }
        })
        viewModel.loadFiles()
    }

    companion object {
        private const val EXTRA_SELECTED_FILE = "extra-media-file"

        fun newIntent(context: Context, file: File): Intent {
            return Intent(context, MediaDetailActivity::class.java).apply {
                putExtra(EXTRA_SELECTED_FILE, file)
            }
        }
    }
}