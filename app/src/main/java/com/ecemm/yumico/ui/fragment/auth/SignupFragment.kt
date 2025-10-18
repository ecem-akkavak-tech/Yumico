package com.ecemm.yumico.ui.fragment.auth
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.ecemm.yumico.R
import com.ecemm.yumico.databinding.FragmentSignupBinding
import com.ecemm.yumico.ui.viewmodel.auth.AuthViewModel


class SignupFragment : Fragment() {
    private lateinit var binding:FragmentSignupBinding
    private val authViewModel: AuthViewModel by activityViewModels() //todo: shared view model
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        //todo- dataBinding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup , container, false)
        binding.signupObject = this
        binding.foodTitle = "Discover \ndelicious food"


        //Authentication -Signup
        observeProcess()
        binding.buttonSignup.setOnClickListener {
            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()

            if(email.isNotEmpty() && password.isNotEmpty()){
                authViewModel.signup(email, password)
            }else {
                Toast.makeText(requireContext(), "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show()
            }

        }


        //Login fragment'a geçiş
        binding.textViewLogin.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.loginFragment)
        }
        return binding.root
    }

  fun observeProcess(){
      authViewModel.user.observe(viewLifecycleOwner){ user->
          if(user != null){
              //anasayfaya yönlendirme & email değerini gönderme
              Toast.makeText(requireContext(), "Hoşgeldin ${user.email}", Toast.LENGTH_SHORT).show()
              val anasayfaGecis= SignupFragmentDirections.signupAnasayfaGecis(user.email?:"")
              Navigation.findNavController(binding.root).navigate(anasayfaGecis)
          }
      }
      authViewModel.errorMessage.observe(viewLifecycleOwner) { error ->
          error?.let {
              Toast.makeText(requireContext(), "Hata: $it", Toast.LENGTH_SHORT).show()
          }
      }
  }
}

/*
  by viewModels():
     -ViewModel sadece bu fragment’a özgüyse
     -Ekran değiştiğinde sıfırlanır

  by activityViewModels():
     -ViewModel Activity’deki tüm fragment’larda ortak kullanılacaksa
     -Fragment’lar arası veri paylaşımı sağlanır
     -örneğin: Auth işlemleri, FirebaseUser state
*/


