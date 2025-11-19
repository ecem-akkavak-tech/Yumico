package com.ecemm.yumico
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.ecemm.yumico.databinding.ActivityMainBinding
import com.ecemm.yumico.ui.viewmodel.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // NAV HOST & BOTTOM NAVIGATION VIEW
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        NavigationUI.setupWithNavController(binding.bottomNavigationView, navController)

        //startDestination (kullanıcı login ise Anasayfa , değilse Signup)
        if (savedInstanceState == null) {
            val navGraph = navController.navInflater.inflate(R.navigation.activity_main_nav)
            navGraph.setStartDestination(
                if (authViewModel.isUserLoggedIn()) R.id.anasayfaFragment else R.id.signupFragment
            )
            navController.graph = navGraph
        }

        //start destination durumuna göre bottom nav bar visibility
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {

                R.id.signupFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
                R.id.loginFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
                R.id.profilBilgisiFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
                R.id.favorilerFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
                R.id.sepetFragment -> {
                    binding.bottomNavigationView.visibility = View.GONE
                }
                else -> {
                    binding.bottomNavigationView.visibility = View.VISIBLE
                }

            }
        }



        // Geri tuşu davranışı
        onBackPressedDispatcher.addCallback(this) {
            val current = navController.currentDestination?.id
            if (current == R.id.anasayfaFragment) finish()
            else navController.popBackStack()
        }

    }
}
