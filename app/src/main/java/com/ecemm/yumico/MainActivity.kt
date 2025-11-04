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
    private lateinit var binding:ActivityMainBinding
    private val authViewModel: AuthViewModel by viewModels() //session için

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Session için öncelikle -  Navigation Controller'ı bul
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.navHostFragment) as NavHostFragment

        val navController = navHostFragment.navController

        //geri tuşuna basınca signup’a geri dönülmemesi için stackten signupı çıkarmalıyız:
        val navLoginOptions = NavOptions.Builder()
            .setPopUpTo(R.id.signupFragment, true) // signupFragment’ı stack’ten temizle
            .build()

        val navSignupOptions = NavOptions.Builder()
            .setPopUpTo(R.id.profilBilgisiFragment, true) // profilBilgisiFragment’ı stack’ten temizle
            .build()

        // Bu işlemle kullanıcı login durumunu “hatırlar” logout olana kadar hep açık kalır
        binding.root.post {
            if (authViewModel.isUserLoggedIn()) {
                if (navController.currentDestination?.id != R.id.anasayfaFragment) {
                    navController.navigate(R.id.anasayfaFragment, null, navLoginOptions)
                }
            } else {
                if (navController.currentDestination?.id != R.id.signupFragment) {
                    navController.navigate(R.id.signupFragment, null, navSignupOptions)
                }else{
                    navController.navigate(R.id.loginFragment)
                }
            }
        }

    }
}