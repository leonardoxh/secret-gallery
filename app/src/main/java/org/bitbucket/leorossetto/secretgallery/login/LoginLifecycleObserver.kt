package org.bitbucket.leorossetto.secretgallery.login

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.content.Intent

class LoginLifecycleObserver(private val context: Context): LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart() {
        context.startActivity(Intent(context, LoginActivity::class.java))
    }
}