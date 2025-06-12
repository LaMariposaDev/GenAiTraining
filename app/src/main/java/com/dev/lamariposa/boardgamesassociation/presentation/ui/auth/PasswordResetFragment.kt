package com.dev.lamariposa.boardgamesassociation.presentation.ui.auth

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.dev.lamariposa.boardgamesassociation.databinding.FragmentPasswordResetBinding
import com.dev.lamariposa.boardgamesassociation.presentation.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Password reset screen fragment
 */
class PasswordResetFragment : Fragment() {

    private var _binding: FragmentPasswordResetBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by activityViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPasswordResetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("PasswordResetFragment", "PasswordResetFragment view created")
        
        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        // Observe loading state
        authViewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.btnResetPassword.isEnabled = !isLoading
        }

        // Observe auth errors
        authViewModel.authError.observe(viewLifecycleOwner) { error ->
            error?.let {
                Log.e("PasswordResetFragment", "Auth error: $it")
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                authViewModel.clearError()
            }
        }
    }

    private fun setupClickListeners() {
        // Reset password button
        binding.btnResetPassword.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            
            if (validateInput(email)) {
                Log.d("PasswordResetFragment", "Sending password reset email to: $email")
                sendPasswordResetEmail(email)
            }
        }

        // Back to login text
        binding.tvBackToLogin.setOnClickListener {
            Log.d("PasswordResetFragment", "Navigating back to login screen")
            findNavController().popBackStack()
        }
    }

    private fun validateInput(email: String): Boolean {
        if (email.isEmpty()) {
            binding.tilEmail.error = "Email is required"
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = "Enter a valid email address"
            return false
        }
        
        binding.tilEmail.error = null
        return true
    }

    private fun sendPasswordResetEmail(email: String) {
        authViewModel.sendPasswordResetEmail(email)
        
        // Show a success message regardless of whether the email exists
        // This is a security best practice to not reveal whether an email is registered
        Toast.makeText(
            requireContext(),
            "If an account exists with this email, a password reset link has been sent",
            Toast.LENGTH_LONG
        ).show()
        
        // Go back to login screen
        findNavController().popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}