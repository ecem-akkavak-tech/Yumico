package com.ecemm.yumico.ui.fragment.auth
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.ecemm.yumico.R
import com.ecemm.yumico.databinding.FragmentSignoutBinding
import com.ecemm.yumico.ui.viewmodel.SepetViewModel
import com.ecemm.yumico.ui.viewmodel.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignoutFragment : Fragment() {
    private val viewModel: AuthViewModel by activityViewModels()

    private lateinit var binding:FragmentSignoutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signout , container, false)


        binding.buttonSignout.setOnClickListener {
            viewModel.signout()
            // Signup fragment’a yönlendir
            findNavController().navigate(R.id.signupFragment)
        }




        return binding.root
    }


}