package com.ecemm.yumico.ui.fragment
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ecemm.yumico.R
import com.ecemm.yumico.databinding.FragmentHesabimBinding
import com.ecemm.yumico.ui.fragment.auth.SignoutFragment
import com.ecemm.yumico.ui.viewmodel.auth.AuthViewModel
import android.util.Base64
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HesabimFragment : Fragment() {
    private lateinit var binding:FragmentHesabimBinding
    private val viewModel : AuthViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hesabim , container, false)

        //get (read) user - firestore
        viewModel.getUserFromFirestore(){ user ->
            if(user != null){
                //Base64 profileImgUrl varsa göster
                if (!user.profileImgUrl.isNullOrEmpty()) {
                    val bytes = Base64.decode(user.profileImgUrl, Base64.DEFAULT)
                    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
                    binding.imageViewProfil.setImageBitmap(bitmap)
                }
                binding.textViewIsim.text = user.name
                binding.textViewSoyisim.text = user.surname

                binding.textViewAdres.text = user.address
                binding.textViewTelefon.text = user.telephone
            }
        }

        binding.textViewEmail.text = viewModel.getCurrentUserEmail() //email

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