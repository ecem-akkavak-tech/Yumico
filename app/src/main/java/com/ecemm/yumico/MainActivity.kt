package com.ecemm.yumico

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
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

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController

        // stack temizleme ayarları
        val navLoginOptions = NavOptions.Builder()
            .setPopUpTo(R.id.signupFragment, true)
            .build()

        val navSignupOptions = NavOptions.Builder()
            .setPopUpTo(R.id.anasayfaFragment, true)
            .build()


        binding.root.post {
            if (authViewModel.isUserLoggedIn()) {
                // kullanıcı login → direkt ana sayfa
                if (navController.currentDestination?.id != R.id.anasayfaFragment) {
                    navController.navigate(R.id.anasayfaFragment, null, navLoginOptions)
                }
            } else {
                // kullanıcı login değil → signup’a at
                if (navController.currentDestination?.id != R.id.signupFragment) {
                    navController.navigate(R.id.signupFragment, null, navSignupOptions)
                }
            }
        }
    }
}
