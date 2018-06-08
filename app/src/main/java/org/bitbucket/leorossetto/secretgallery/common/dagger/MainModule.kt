package org.bitbucket.leorossetto.secretgallery.common.dagger

import android.app.Application
import com.github.leonardoxh.keystore.CipherStorage
import com.github.leonardoxh.keystore.CipherStorageFactory
import dagger.Module
import dagger.Provides
import org.bitbucket.leorossetto.secretgallery.login.LoginRepository

@Module
class MainModule(private val application: Application) {
    @Provides
    fun provideApplication(): Application = application

    @Provides
    fun provideCipherStorage(): CipherStorage = CipherStorageFactory.newInstance(application)

    @Provides
    fun provideLoginRepository(cipherStorage: CipherStorage): LoginRepository =
            LoginRepository(cipherStorage)
}