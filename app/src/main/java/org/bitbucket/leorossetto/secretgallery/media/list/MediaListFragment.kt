package org.bitbucket.leorossetto.secretgallery.media.list

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.view.ActionMode
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Menu
import kotlinx.android.synthetic.main.fragment_media_list.*
import org.bitbucket.leorossetto.secretgallery.R
import org.bitbucket.leorossetto.secretgallery.common.dagger.appComponent
import org.bitbucket.leorossetto.secretgallery.media.detail.MediaDetailActivity
import org.bitbucket.leorossetto.secretgallery.media.select.MediaSelectViewModel
import java.io.File

//TODO - save the selected state into a bundle for rotation
class MediaListFragment: Fragment(), ActionMode.Callback, MediaListAdapter.MediaListClickCallback {
    private var mediaSelectViewModel: MediaSelectViewModel? = null
    private var mediaListViewModel: MediaListViewModel? = null
    private var currentActionMode: ActionMode? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_media_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mediaSelectViewModel = ViewModelProviders.of(this,
                appComponent().mediaSelectViewModelFactory())
                .get(MediaSelectViewModel::class.java)
        mediaListViewModel = ViewModelProviders.of(this,
                appComponent().mediaListViewModelFactory())
                .get(MediaListViewModel::class.java)

        mediaList.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = MediaListAdapter(this@MediaListFragment)
        }
        mediaListViewModel?.filesLiveData?.observe(this, Observer { files ->
            (mediaList.adapter as? MediaListAdapter)?.submitList(files)
        })
        mediaListViewModel?.loadFiles()

        importContent.setOnClickListener {
            startActivityForResult(mediaSelectViewModel?.newImportIntent(), PICK_FILE_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_FILE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            mediaSelectViewModel?.onActivityResult(data)
        }
    }

    override fun onMediaActivated(file: File, isActivated: Boolean, currentActivatedItemCount: Int) {
        if (currentActionMode == null) {
            currentActionMode = (activity as? AppCompatActivity)?.startSupportActionMode(this)
        }

        if (currentActivatedItemCount == 0) {
            currentActionMode?.finish()
            currentActionMode = null
            return
        }
        currentActionMode?.title = getString(R.string.media_list_action_mode_item_selected_placeholder, currentActivatedItemCount)
    }

    override fun onDestroyView() {
        currentActionMode?.finish()
        super.onDestroyView()
    }

    override fun onMediaClick(file: File) {
        startActivity(MediaDetailActivity.newIntent(requireContext(), file))
    }

    override fun onActionItemClicked(mode: ActionMode?, item: MenuItem?): Boolean {
        if (item?.itemId == R.id.delete_media) {
            val selectedFiles = (mediaList.adapter as MediaListAdapter).getSelectedItems()
            mediaSelectViewModel?.removeFiles(selectedFiles)
            currentActionMode?.finish()
            currentActionMode = null
            return true
        }

        if (item?.itemId == R.id.share_media) {
            val selectedFiles = (mediaList.adapter as MediaListAdapter).getSelectedItems()
            mediaSelectViewModel?.startShareIntentWith(requireActivity(), selectedFiles)
            return true
        }
        return false
    }

    override fun onCreateActionMode(mode: ActionMode?, menu: Menu?): Boolean {
        mode?.menuInflater?.inflate(R.menu.menu_media_action_mode, menu)
        return true
    }

    override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean = false

    override fun onDestroyActionMode(mode: ActionMode?) {
        (mediaList.adapter as? MediaListAdapter)?.clearSelection()
        currentActionMode = null
    }

    companion object {
        private const val PICK_FILE_REQUEST_CODE = 1

        fun newInstance(): MediaListFragment = MediaListFragment()
    }
}