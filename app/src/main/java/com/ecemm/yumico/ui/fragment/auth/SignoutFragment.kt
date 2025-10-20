package com.ecemm.yumico.ui.fragment.auth
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.ecemm.yumico.R
import com.ecemm.yumico.databinding.FragmentSignoutBinding

class SignoutFragment : Fragment() {
    private lateinit var binding:FragmentSignoutBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signout , container, false)










        return binding.root
    }


}