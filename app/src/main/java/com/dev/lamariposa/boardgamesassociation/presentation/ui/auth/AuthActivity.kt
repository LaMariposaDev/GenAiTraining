package com.dev.lamariposa.boardgamesassociation.presentation.ui.auth

import android.os.Bundle
import android.util.Log
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
            // Handle navigation or UI updates based on auth state
        }
    }
}
