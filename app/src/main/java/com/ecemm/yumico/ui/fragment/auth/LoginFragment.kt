package com.ecemm.yumico.ui.fragment.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.ecemm.yumico.R
import com.ecemm.yumico.databinding.FragmentLoginBinding
import com.ecemm.yumico.databinding.FragmentSignupBinding
import com.ecemm.yumico.ui.viewmodel.auth.AuthViewModel

class LoginFragment : Fragment() {
    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: AuthViewModel //viewmodel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        //todo- dataBinding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        binding.loginObject = this
        binding.titleFood= "Discover \ndelicious food"

        binding.textViewSignup.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.signupFragment)
        }
        return binding.root

    }
}