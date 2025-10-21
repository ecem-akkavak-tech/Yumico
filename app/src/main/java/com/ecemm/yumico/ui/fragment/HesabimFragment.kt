package com.ecemm.yumico.ui.fragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.ecemm.yumico.R
import com.ecemm.yumico.databinding.FragmentHesabimBinding
import com.ecemm.yumico.ui.fragment.auth.SignoutFragment

class HesabimFragment : Fragment() {
    private lateinit var binding:FragmentHesabimBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hesabim , container, false)


        //todo- Signout fragment'ı container içine eklemek için:
        childFragmentManager.beginTransaction()
            //böylece ilgili fragmentı ve onun xml'ini ->hesabim xml'e veriyoruz
            //not: fragmenta ait xml'i FrameLayout ile ver
            .replace(R.id.signoutFragmentContainer, SignoutFragment())
            .commit()


        binding.buttonCloseKullanici.setOnClickListener {
            findNavController().popBackStack() //1 önceki fragmenta gider
        }

        return binding.root






    }



}