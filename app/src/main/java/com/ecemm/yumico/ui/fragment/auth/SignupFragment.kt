package com.ecemm.yumico.ui.fragment.auth
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
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


        return binding.root
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


