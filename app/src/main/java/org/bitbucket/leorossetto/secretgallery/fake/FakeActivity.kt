package org.bitbucket.leorossetto.secretgallery.fake

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.include_toolbar.*
import org.bitbucket.leorossetto.secretgallery.R

class FakeActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fake)
        setSupportActionBar(toolbar)
    }

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, FakeActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or
                        Intent.FLAG_ACTIVITY_CLEAR_TOP
            }
        }
    }
}