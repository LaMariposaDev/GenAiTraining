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
import org.koin.androidx.viewmodel.ext.android.activityViewModel

/**
 * Email verification screen fragment
 */
class EmailVerificationFragment : Fragment() {

    private var _binding: FragmentEmailVerificationBinding? = null
    private val binding get() = _binding!!

    private val authViewModel: AuthViewModel by activityViewModel()

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
        // Authentication state is observed by the AuthActivity which handles all navigation
        
        // Observe user for displaying email
        authViewModel.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                binding.tvEmail.text = user.email
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
            // Just sign out - AuthActivity will handle navigation back to login
            authViewModel.signOut()
        }

        // Sign out text
        binding.tvSignOut.setOnClickListener {
            Log.d("EmailVerificationFragment", "Signing out user")
            authViewModel.signOut()
        }
    }

    // AuthActivity handles the navigation to MainActivity when user is authenticated

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}