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
import com.dev.lamariposa.boardgamesassociation.databinding.FragmentEmailVerificationBinding
import com.dev.lamariposa.boardgamesassociation.domain.model.AuthState
import com.dev.lamariposa.boardgamesassociation.presentation.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Email verification screen fragment
 */
class EmailVerificationFragment : Fragment() {

    private var _binding: FragmentEmailVerificationBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEmailVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("EmailVerificationFragment", "EmailVerificationFragment view created")
        
        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        // Observe user
        authViewModel.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                binding.tvEmail.text = user.email
                
                if (user.isEmailVerified) {
                    Log.d("EmailVerificationFragment", "User's email is verified, navigating to main screen")
                    navigateToMainScreen()
                }
            } else {
                Log.d("EmailVerificationFragment", "No user is signed in, navigating back to login screen")
                findNavController().navigate(R.id.action_emailVerificationFragment_to_loginFragment)
            }
        }

        // Observe authentication state
        authViewModel.authState.observe(viewLifecycleOwner) { state ->
            Log.d("EmailVerificationFragment", "Auth state changed: $state")
            when (state) {
                AuthState.AUTHENTICATED -> {
                    Log.d("EmailVerificationFragment", "User is authenticated and verified, navigating to main screen")
                    navigateToMainScreen()
                }
                AuthState.UNVERIFIED_EMAIL -> {
                    // Stay on this screen
                }
                AuthState.UNAUTHENTICATED -> {
                    Log.d("EmailVerificationFragment", "User is not authenticated, navigating to login screen")
                    findNavController().navigate(R.id.action_emailVerificationFragment_to_loginFragment)
                }
                AuthState.LOADING -> {
                    // Show loading if needed
                }
            }
        }

        // Observe auth errors
        authViewModel.authError.observe(viewLifecycleOwner) { error ->
            error?.let {
                Log.e("EmailVerificationFragment", "Auth error: $it")
                Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                authViewModel.clearError()
            }
        }
    }

    private fun setupClickListeners() {
        // Resend verification email button
        binding.btnResendVerification.setOnClickListener {
            Log.d("EmailVerificationFragment", "Resending verification email")
            authViewModel.sendEmailVerification()
            Toast.makeText(
                requireContext(),
                "Verification email sent. Please check your inbox.",
                Toast.LENGTH_LONG
            ).show()
        }

        // Refresh button (check if email is verified)
        binding.btnRefresh.setOnClickListener {
            Log.d("EmailVerificationFragment", "Refreshing user state to check email verification")
            // The Firebase authentication state listener should handle this automatically,
            // but we can force a sign-out and sign-in to refresh the token
            authViewModel.signOut()
            findNavController().navigate(R.id.action_emailVerificationFragment_to_loginFragment)
        }

        // Sign out text
        binding.tvSignOut.setOnClickListener {
            Log.d("EmailVerificationFragment", "Signing out user")
            authViewModel.signOut()
        }
    }

    private fun navigateToMainScreen() {
        Log.d("EmailVerificationFragment", "Navigating to main screen")
        findNavController().navigate(R.id.action_emailVerificationFragment_to_mainFragment)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}