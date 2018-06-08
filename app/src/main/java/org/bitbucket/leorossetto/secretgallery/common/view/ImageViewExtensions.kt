package org.bitbucket.leorossetto.secretgallery.common.view

import android.widget.ImageView
import com.squareup.picasso.Picasso
import java.io.File

fun ImageView.setImageFile(file: File) {
    Picasso.get()
            .load(file)
            .into(this)
}