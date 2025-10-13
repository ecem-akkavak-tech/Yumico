package com.ecemm.yumico.ui.fragment.auth
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ecemm.yumico.R
import com.ecemm.yumico.databinding.FragmentSignupBinding
import com.ecemm.yumico.ui.viewmodel.auth.AuthViewModel


class SignupFragment : Fragment() {
    private lateinit var binding:FragmentSignupBinding
    private lateinit var viewModel: AuthViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSignupBinding.inflate(inflater, container, false)
        //todo- dataBinding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup , container, false)
        binding.signupObject = this
        binding.foodTitle = "Discover \ndelicious food"


        return binding.root
    }


}