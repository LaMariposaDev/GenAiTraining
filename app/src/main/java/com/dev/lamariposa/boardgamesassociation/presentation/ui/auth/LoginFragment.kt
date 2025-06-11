package com.dev.lamariposa.boardgamesassociation.presentation.ui.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dev.lamariposa.boardgamesassociation.R
import com.dev.lamariposa.boardgamesassociation.databinding.FragmentLoginBinding
import com.dev.lamariposa.boardgamesassociation.domain.model.AuthState
import com.dev.lamariposa.boardgamesassociation.presentation.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import android.util.Log
import com.dev.lamariposa.boardgamesassociation.MainActivity

/**
 * Login screen fragment
 */
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("LoginFragment", "LoginFragment view created")
        
        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        // Observe authentication state
        authViewModel.authState.observe(viewLifecycleOwner) { state ->
            Log.d("LoginFragment", "Auth state changed: $state")
            when (state) {
                AuthState.AUTHENTICATED -> {
                    Log.d("LoginFragment", "User is authenticated, navigating to main screen")
                    navigateToMainScreen()
                }
                AuthState.UNVERIFIED_EMAIL -> {
                    Log.d("LoginFragment", "User's email is not verified, navigating to email verification screen")
                    navigateToEmailVerification()
                }
                AuthState.UNAUTHENTICATED -> {
                    // Stay on login screen
                }
                AuthState.LOADING -> {
                    // Show loading if needed
                }
            }
        }

        // Observe loading state
        authViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.btnLogin.isEnabled = !isLoading
        }

        // Observe auth errors
        authViewModel.authError.observe(viewLifecycleOwner) { error ->
            error?.let {
                Log.e("LoginFragment", "Auth error: $it")
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                authViewModel.clearError()
            }
        }
    }

    private fun setupClickListeners() {
        // Login button
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            
            if (validateInputs(email, password)) {
                Log.d("LoginFragment", "Attempting login with email: $email")
                authViewModel.signIn(email, password)
            }
        }

        // Sign up text
        binding.tvSignUp.setOnClickListener {
            Log.d("LoginFragment", "Navigating to register screen")
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        // Forgot password text
        binding.tvForgotPassword.setOnClickListener {
            Log.d("LoginFragment", "Navigating to password reset screen")
            findNavController().navigate(R.id.action_loginFragment_to_passwordResetFragment)
        }
    }

    private fun validateInputs(email: String, password: String): Boolean {
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

        return isValid
    }

    private fun navigateToMainScreen() {
        Log.d("LoginFragment", "Navigating to main screen")
        requireActivity().startActivity(
            android.content.Intent(
                requireContext(),
                MainActivity::class.java
            )
        )

    }

    private fun navigateToEmailVerification() {
        Log.d("LoginFragment", "Navigating to email verification screen")
        findNavController().navigate(R.id.action_loginFragment_to_emailVerificationFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}