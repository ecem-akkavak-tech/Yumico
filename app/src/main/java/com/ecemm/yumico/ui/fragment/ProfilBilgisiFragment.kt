package com.ecemm.yumico.ui.fragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ecemm.yumico.R
import com.ecemm.yumico.databinding.FragmentProfilBilgisiBinding

class ProfilBilgisiFragment : Fragment() {
    private lateinit var binding:FragmentProfilBilgisiBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProfilBilgisiBinding.inflate(inflater, container, false)
        return binding.root
    }

}