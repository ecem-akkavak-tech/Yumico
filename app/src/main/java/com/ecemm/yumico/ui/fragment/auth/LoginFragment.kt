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
import com.ecemm.yumico.databinding.FragmentLoginBinding
import com.ecemm.yumico.databinding.FragmentSignupBinding
import com.ecemm.yumico.ui.viewmodel.auth.AuthViewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private val authViewModel: AuthViewModel by activityViewModels() //todo: shared view model
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        //todo- dataBinding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.loginObject = this
        binding.titleFood= "Discover \ndelicious food"

        //Authentication -Login
        observeProcess()
        binding.buttonLogin.setOnClickListener {
            val email = binding.editTextEmailLogin.text.toString().trim()
            val password = binding.editTextPasswordLogin.text.toString().trim()
            if(email.isNotEmpty() && password.isNotEmpty()){
                authViewModel.login(email,password)
            }else {
                Toast.makeText(requireContext(), "Lütfen tüm alanları doldurun", Toast.LENGTH_SHORT).show()
            }
        }

        //Signup fragment'a geçiş
        binding.textViewSignup.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.signupFragment)
        }
        return binding.root

    }

    fun observeProcess(){
        authViewModel.user.observe(viewLifecycleOwner){ user->
            if(user != null){
                //anasayfaya yönlendirme & email değerini gönderme
                Toast.makeText(requireContext(), "Hoşgeldin ${user.email}", Toast.LENGTH_SHORT).show()
                val anasayfaGecis= LoginFragmentDirections.loginAnasayfaGecis(user.email?:"")
                Navigation.findNavController(binding.root).navigate(anasayfaGecis)

            }
        }
        authViewModel.errorMessage.observe(viewLifecycleOwner) { error ->
            error?.let {
                val mesaj = when {
                    it.contains("email") -> "E-posta formatı hatalı"
                    it.contains("password") -> "Şifre yanlış veya eksik"
                    it.contains("no user") -> "Bu e-posta ile kayıtlı kullanıcı bulunamadı"
                    else -> "Bilinmeyen bir hata oluştu"
                }

                Toast.makeText(requireContext(), mesaj, Toast.LENGTH_SHORT).show()
            }
        }
    }
}