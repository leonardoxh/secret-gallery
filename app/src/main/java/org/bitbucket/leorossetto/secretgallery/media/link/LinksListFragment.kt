package org.bitbucket.leorossetto.secretgallery.media.link

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.bitbucket.leorossetto.secretgallery.R

class LinksListFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_links_list, container, false)

    companion object {
        fun newInstance(): LinksListFragment = LinksListFragment()
    }
}