package org.bitbucket.leorossetto.secretgallery.common.dagger

import android.app.Activity
import android.support.v4.app.Fragment
import org.bitbucket.leorossetto.secretgallery.AppApplication

fun Activity.appComponent(): AppComponent =
        (application as AppApplication).appComponent

fun Fragment.appComponent(): AppComponent =
        requireActivity().appComponent()