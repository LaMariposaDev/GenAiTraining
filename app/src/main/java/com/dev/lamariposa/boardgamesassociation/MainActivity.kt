package com.dev.lamariposa.boardgamesassociation

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dev.lamariposa.boardgamesassociation.databinding.ActivityMainBinding
import com.dev.lamariposa.boardgamesassociation.domain.model.AuthState
import com.dev.lamariposa.boardgamesassociation.presentation.viewmodel.AuthViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val authViewModel: AuthViewModel by viewModel()
    
    private val authDestinations = setOf(
        R.id.loginFragment,
        R.id.registerFragment,
        R.id.passwordResetFragment,
        R.id.emailVerificationFragment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity","created")

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        navController.addOnDestinationChangedListener(this)
        
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.searchFragment, R.id.myGamesFragment
            )
        )
        
        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)
        
        setupAuthObserver()
    }
    
    private fun setupAuthObserver() {
        Log.d("MainActivity", "Setting up auth observer")
        authViewModel.authState.observe(this) { state ->
            Log.d("MainActivity", "Auth state changed: $state")
            when (state) {
                AuthState.AUTHENTICATED -> {
                    Log.d("MainActivity", "User authenticated, ensuring on main navigation")
                    // No-op: MainActivity only handles main app navigation now
                }
                AuthState.UNVERIFIED_EMAIL -> {
                    Log.d("MainActivity", "User's email not verified")
                }
                AuthState.UNAUTHENTICATED -> {
                    Log.d("MainActivity", "User not authenticated, launching AuthActivity")
                    startActivity(
                        android.content.Intent(this, com.dev.lamariposa.boardgamesassociation.presentation.ui.auth.AuthActivity::class.java)
                    )
                    finish()
                }
                AuthState.LOADING -> {
                    // Do nothing while loading
                }
            }
        }
    }
    
    private fun isOnAuthScreen(): Boolean {
        val currentDestId = navController.currentDestination?.id
        return currentDestId in authDestinations
    }
    
    private fun isOnVerificationScreen(): Boolean {
        return navController.currentDestination?.id == R.id.emailVerificationFragment
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        // Hide bottom navigation on auth screens
        if (destination.id in authDestinations) {
            binding.navView.visibility = View.GONE
        } else {
            binding.navView.visibility = View.VISIBLE
        }
    }
}