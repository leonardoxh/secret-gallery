package org.bitbucket.leorossetto.secretgallery

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.WindowManager.LayoutParams.FLAG_SECURE
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.include_toolbar.*
import org.bitbucket.leorossetto.secretgallery.media.link.LinksListFragment
import org.bitbucket.leorossetto.secretgallery.media.list.MediaListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(FLAG_SECURE)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        if (savedInstanceState == null) {
            replaceContent(MediaListFragment.newInstance())
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            title = menuItem.title
            if (menuItem.itemId == R.id.bottom_item_gallery) {
                replaceContent(MediaListFragment.newInstance())
            } else {
                replaceContent(LinksListFragment.newInstance())
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun replaceContent(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .commit()
    }
}
