package com.dev.lamariposa.boardgamesassociation.presentation.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.dev.lamariposa.boardgamesassociation.R
import org.koin.androidx.viewmodel.ext.android.viewModel
import com.dev.lamariposa.boardgamesassociation.presentation.viewmodel.AuthViewModel

class AuthActivity : AppCompatActivity() {
    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)
        Log.d("AuthActivity","created")
        setupAuthObserver()
    }

    private fun setupAuthObserver() {
        Log.d("AuthActivity","Setting up auth observer")
        authViewModel.authState.observe(this) { state ->
            Log.d("AuthActivity","Auth state changed: $state")
            val loadingIndicator = findViewById<View>(R.id.auth_loading_indicator)
            when (state) {
                com.dev.lamariposa.boardgamesassociation.domain.model.AuthState.AUTHENTICATED -> {
                    loadingIndicator?.visibility = View.GONE
                    Log.d("AuthActivity", "User authenticated, moving to MainActivity")
                    val intent = android.content.Intent(this, com.dev.lamariposa.boardgamesassociation.MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
                com.dev.lamariposa.boardgamesassociation.domain.model.AuthState.UNAUTHENTICATED -> {
                    loadingIndicator?.visibility = View.GONE
                    Log.d("AuthActivity", "User not authenticated, stay on AuthActivity")
                    android.widget.Toast.makeText(this, getString(com.dev.lamariposa.boardgamesassociation.R.string.login_failed), android.widget.Toast.LENGTH_SHORT).show()
                }
                com.dev.lamariposa.boardgamesassociation.domain.model.AuthState.UNVERIFIED_EMAIL -> {
                    loadingIndicator?.visibility = View.GONE
                    Log.d("AuthActivity", "User authenticated but email not verified")
                    android.widget.Toast.makeText(this, getString(com.dev.lamariposa.boardgamesassociation.R.string.email_not_verified), android.widget.Toast.LENGTH_LONG).show()
                }
                com.dev.lamariposa.boardgamesassociation.domain.model.AuthState.LOADING -> {
                    Log.d("AuthActivity", "Auth state loading")
                    loadingIndicator?.visibility = View.VISIBLE
                }
            }
        }
    }
}