package org.bitbucket.leorossetto.secretgallery.login

import com.github.leonardoxh.keystore.CipherStorage

class LoginRepository(private val cipherStorage: CipherStorage) {
    internal fun hasCredentials(): Boolean = cipherStorage.containsAlias(PIN_KEY)
            && cipherStorage.containsAlias(PIN_FAKE_KEY)

    internal fun isCredentialValid(credential: String): Boolean =
            cipherStorage.decrypt(PIN_KEY) == credential

    internal fun isFakeCredential(credential: String): Boolean =
            cipherStorage.decrypt(PIN_FAKE_KEY) == credential

    internal fun storeCredential(credential: String, fakeCredential: String) {
        cipherStorage.encrypt(PIN_KEY, credential)
        cipherStorage.encrypt(PIN_FAKE_KEY, fakeCredential)
    }

    internal fun canStoreCredentials(credential: String, fakeCredential: String): Boolean =
            credential != fakeCredential

    companion object {
        private const val PIN_KEY = "pin"
        private const val PIN_FAKE_KEY = "fake-pin"
    }
}