package org.bitbucket.leorossetto.secretgallery.media.list

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_media.view.*
import org.bitbucket.leorossetto.secretgallery.R
import org.bitbucket.leorossetto.secretgallery.common.isVideo
import org.bitbucket.leorossetto.secretgallery.common.view.setImageFile
import java.io.File

class MediaListAdapter(private val itemClick: MediaListClickCallback):
        ListAdapter<File, MediaListAdapter.MediaListViewHolder>(FILES_DIFF) {
    private val selectedItems = HashSet<File>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaListViewHolder {
        val v = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_media, parent, false)

        return MediaListViewHolder(v)
    }

    override fun onBindViewHolder(holder: MediaListViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    fun clearSelection() {
        selectedItems.clear()
        notifyDataSetChanged()
    }

    fun getSelectedItems(): Set<File> = selectedItems

    inner class MediaListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                val file = getItem(layoutPosition)
                if (selectedItems.isEmpty()) {
                    itemClick.onMediaClick(file)
                    return@setOnClickListener
                }

                activateItem()
            }
            itemView.setOnLongClickListener {
                activateItem()
                return@setOnLongClickListener true
            }
        }

        private fun activateItem() {
            val file = getItem(layoutPosition)
            if (selectedItems.contains(file)) {
                selectedItems.remove(file)
                itemClick.onMediaActivated(file, false, selectedItems.size)
            } else {
                selectedItems.add(file)
                itemClick.onMediaActivated(file, true, selectedItems.size)
            }
            notifyItemChanged(layoutPosition)
        }

        fun bind(file: File) {
            if (file.isVideo()) {
                itemView.videoPlay.visibility = View.VISIBLE
            } else {
                itemView.videoPlay.visibility = View.GONE
            }
            itemView.isActivated = selectedItems.contains(file)
            itemView.thumb.setImageFile(file)
        }
    }

    interface MediaListClickCallback {
        fun onMediaClick(file: File)
        fun onMediaActivated(file: File, isActivated: Boolean, currentActivatedItemCount: Int)
    }

    companion object {
        val FILES_DIFF = object : DiffUtil.ItemCallback<File>() {
            override fun areItemsTheSame(oldItem: File?, newItem: File?): Boolean =
                    oldItem == newItem

            override fun areContentsTheSame(oldItem: File?, newItem: File?): Boolean =
                    oldItem == newItem
        }
    }
}