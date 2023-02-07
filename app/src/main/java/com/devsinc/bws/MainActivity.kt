package com.devsinc.bws

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.devsinc.bws.databinding.ActivityMainBinding
import com.devsinc.bws.model.Customer
import com.devsinc.bws.repository.Resource
import com.devsinc.bws.viewmodel.AuthViewModel
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfiguration: AppBarConfiguration
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        // Handle the splash screen transition.
        installSplashScreen()

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment,
                R.id.profileFragment,
                R.id.findVenueFragment,
                R.id.bookOnlineFragment,
                R.id.findClassFragment,
                R.id.bookClassFragment,
                R.id.findTeammatesFragment,
                R.id.myBookingsFragment,
                R.id.notificationsFragment,
                R.id.offersFragment,
                R.id.aboutFragment,
                R.id.contactUsFragment,
                R.id.nav_delete_account,
                R.id.nav_logout
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        navView.menu.findItem(R.id.nav_logout).setOnMenuItemClickListener {
            viewModel.logout()
            true
        }
        navView.menu.findItem(R.id.nav_delete_account).setOnMenuItemClickListener {
            Toast.makeText(this, "Delete account", Toast.LENGTH_SHORT).show()
            true
        }
        navView.menu.findItem(R.id.homeFragment).setOnMenuItemClickListener {
            navController.navigate(R.id.homeFragment)
            drawerLayout.close()
            true
        }

        val header = navView.getHeaderView(0)
        val ivProfileImage =
            header.findViewById<com.google.android.material.imageview.ShapeableImageView>(R.id.iv_profile_image)
        val tvUserName =
            header.findViewById<com.google.android.material.textview.MaterialTextView>(R.id.tv_user_name)
        val tvUserEmail =
            header.findViewById<com.google.android.material.textview.MaterialTextView>(R.id.tv_user_email)

        supportActionBar?.hide()
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

        checkIfCustomerLoggedIn()

        lifecycleScope.launchWhenStarted {
            viewModel.loginFlow.collect { event ->
                when (event) {
                    is Resource.Success -> {
                        tvUserName.text = getString(
                            R.string.full_name_join,
                            event.result.first_name,
                            event.result.last_name
                        )
                        tvUserEmail.text = event.result.cus_email
                        Glide.with(this@MainActivity)
                            .load(event.result.cus_photo)
                            .placeholder(R.drawable.ic_baseline_account_circle_24)
                            .into(ivProfileImage)
                        supportActionBar?.show()

                        navController.navigate(R.id.homeFragment)
                    }
                    is Resource.Loading -> {
                        // show progress bar
                    }
                    is Resource.Error -> {
                        // show error message
                    }
                    else -> {
                        supportActionBar?.hide()
                        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
                        navController.navigate(R.id.action_global_signInFragment)
                    }
                }
            }
        }
    }

    fun checkIfCustomerLoggedIn() {
        viewModel.isCustomerLoggedIn()
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}