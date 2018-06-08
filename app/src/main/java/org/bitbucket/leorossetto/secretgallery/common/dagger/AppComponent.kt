package org.bitbucket.leorossetto.secretgallery.common.dagger

import dagger.Component
import org.bitbucket.leorossetto.secretgallery.login.LoginActivity
import org.bitbucket.leorossetto.secretgallery.media.select.MediaSelectViewModelFactory
import org.bitbucket.leorossetto.secretgallery.media.list.MediaListViewModelFactory

@Component(modules = [(MainModule::class)])
interface AppComponent {
    fun inject(loginActivity: LoginActivity)

    fun mediaListViewModelFactory(): MediaListViewModelFactory
    fun mediaSelectViewModelFactory(): MediaSelectViewModelFactory
}