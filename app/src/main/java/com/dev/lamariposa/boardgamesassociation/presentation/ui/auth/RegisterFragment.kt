package com.dev.lamariposa.boardgamesassociation.presentation.ui.auth

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dev.lamariposa.boardgamesassociation.R
import com.dev.lamariposa.boardgamesassociation.databinding.FragmentRegisterBinding
import com.dev.lamariposa.boardgamesassociation.domain.model.AuthState
import com.dev.lamariposa.boardgamesassociation.presentation.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Register screen fragment
 */
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("RegisterFragment", "RegisterFragment view created")
        
        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        // Observe authentication state
        authViewModel.authState.observe(viewLifecycleOwner) { state ->
            Log.d("RegisterFragment", "Auth state changed: $state")
            when (state) {
                AuthState.AUTHENTICATED -> {
                    Log.d("RegisterFragment", "User is authenticated, navigating to main screen")
                    navigateToMainScreen()
                }
                AuthState.UNVERIFIED_EMAIL -> {
                    Log.d("RegisterFragment", "User's email is not verified, navigating to email verification screen")
                    navigateToEmailVerification()
                }
                AuthState.UNAUTHENTICATED -> {
                    // Stay on register screen
                }
                AuthState.LOADING -> {
                    // Show loading if needed
                }
            }
        }

        // Observe loading state
        authViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.btnRegister.isEnabled = !isLoading
        }

        // Observe auth errors
        authViewModel.authError.observe(viewLifecycleOwner) { error ->
            error?.let {
                Log.e("RegisterFragment", "Auth error: $it")
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                authViewModel.clearError()
            }
        }
    }

    private fun setupClickListeners() {
        // Register button
        binding.btnRegister.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val confirmPassword = binding.etConfirmPassword.text.toString().trim()
            
            if (validateInputs(email, password, confirmPassword)) {
                Log.d("RegisterFragment", "Attempting to create account with email: $email")
                authViewModel.createAccount(email, password)
            }
        }

        // Login text
        binding.tvLogin.setOnClickListener {
            Log.d("RegisterFragment", "Navigating back to login screen")
            findNavController().popBackStack()
        }
    }

    private fun validateInputs(email: String, password: String, confirmPassword: String): Boolean {
        var isValid = true

        if (email.isEmpty()) {
            binding.tilEmail.error = "Email is required"
            isValid = false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = "Enter a valid email address"
            isValid = false
        } else {
            binding.tilEmail.error = null
        }

        if (password.isEmpty()) {
            binding.tilPassword.error = "Password is required"
            isValid = false
        } else if (password.length < 6) {
            binding.tilPassword.error = "Password must be at least 6 characters"
            isValid = false
        } else {
            binding.tilPassword.error = null
        }

        if (confirmPassword.isEmpty()) {
            binding.tilConfirmPassword.error = "Confirm password is required"
            isValid = false
        } else if (confirmPassword != password) {
            binding.tilConfirmPassword.error = "Passwords do not match"
            isValid = false
        } else {
            binding.tilConfirmPassword.error = null
        }

        return isValid
    }

    private fun navigateToMainScreen() {
        Log.d("RegisterFragment", "Navigating to main screen")
        findNavController().navigate(R.id.action_registerFragment_to_mainFragment)
    }

    private fun navigateToEmailVerification() {
        Log.d("RegisterFragment", "Navigating to email verification screen")
        findNavController().navigate(R.id.action_registerFragment_to_emailVerificationFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}