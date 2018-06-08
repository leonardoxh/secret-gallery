package org.bitbucket.leorossetto.secretgallery

import android.app.Application
import android.arch.lifecycle.ProcessLifecycleOwner
import com.squareup.picasso.Picasso
import org.bitbucket.leorossetto.secretgallery.common.PicassoVideoRequestHandler
import org.bitbucket.leorossetto.secretgallery.common.dagger.AppComponent
import org.bitbucket.leorossetto.secretgallery.common.dagger.DaggerAppComponent
import org.bitbucket.leorossetto.secretgallery.common.dagger.MainModule
import org.bitbucket.leorossetto.secretgallery.login.LoginLifecycleObserver

class AppApplication: Application() {
    val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
                .mainModule(MainModule(this))
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        val picasso = Picasso.Builder(this)
                .addRequestHandler(PicassoVideoRequestHandler())
                .build()
        Picasso.setSingletonInstance(picasso)

        ProcessLifecycleOwner.get()
                .lifecycle
                .addObserver(LoginLifecycleObserver(this))
    }
}