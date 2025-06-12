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

class MainActivity : AppCompatActivity(), NavController.OnDestinationChangedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    
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
        
        // Authentication logic is now handled only in AuthActivity
    }
    
    // Removed setupAuthObserver and all authentication state handling from MainActivity
    
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