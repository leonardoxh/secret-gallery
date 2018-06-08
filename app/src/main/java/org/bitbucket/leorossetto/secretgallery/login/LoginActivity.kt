package org.bitbucket.leorossetto.secretgallery.login

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import org.bitbucket.leorossetto.secretgallery.R
import org.bitbucket.leorossetto.secretgallery.common.dagger.appComponent
import org.bitbucket.leorossetto.secretgallery.fake.FakeActivity
import javax.inject.Inject

class LoginActivity: AppCompatActivity() {
    @Inject lateinit var loginRepository: LoginRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        appComponent().inject(this)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (!loginRepository.hasCredentials()) {
            createPasswordInfo.visibility = View.VISIBLE
            fakePinInput.visibility = View.VISIBLE
        }
        pinInput.editText?.setOnEditorActionListener { _, _, _ ->
            if (validateCredentials()) {
                setResult(RESULT_OK)
                finish()
            }

            return@setOnEditorActionListener true
        }
    }

    private fun validateCredentials(): Boolean {
        val credential = pinInput.editText?.text.toString()
        val fakeCredential = fakePinInput.editText?.text.toString()
        if (!loginRepository.hasCredentials()) {
            if (!loginRepository.canStoreCredentials(credential, fakeCredential)) {
                Toast.makeText(this, R.string.login_password_pin_fake_equal, Toast.LENGTH_SHORT)
                        .show()
                return false
            }
            loginRepository.storeCredential(credential, fakeCredential)
            return true
        }

        if (loginRepository.isFakeCredential(credential)) {
            startActivity(FakeActivity.newIntent(this))
            finishAffinity()
            return false
        }

        if (!loginRepository.isCredentialValid(credential)) {
            pinInput.error = getText(R.string.login_password_pin_incorrect)
            return false
        }
        return true
    }

    override fun onBackPressed() {
        finishAffinity()
    }
}