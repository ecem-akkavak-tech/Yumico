package com.ecemm.yumico.ui.fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import android.util.Base64
import android.widget.Toast
import com.ecemm.yumico.R
import com.ecemm.yumico.data.entity.Users
import com.ecemm.yumico.databinding.FragmentProfilTamamlaBinding
import com.ecemm.yumico.ui.viewmodel.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.result.contract.ActivityResultContracts

@AndroidEntryPoint
class ProfilTamamlaFragment : Fragment() {
    private lateinit var binding: FragmentProfilTamamlaBinding
    private val viewModel: AuthViewModel by viewModels()
    private var base64Image: String? = null

    // Modern launcher ile resim seçimi
    private val pickImageLauncher = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            val inputStream = requireContext().contentResolver.openInputStream(it)
            val bytes = inputStream?.readBytes()
            base64Image = Base64.encodeToString(bytes, Base64.DEFAULT)
            binding.imageViewProfileImg.setImageURI(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfilTamamlaBinding.inflate(inflater, container, false)

        // Profil resmi seçimi
        binding.imageViewProfileImg.setOnClickListener {
            val permission = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU)
                Manifest.permission.READ_MEDIA_IMAGES
            else
                Manifest.permission.READ_EXTERNAL_STORAGE

            if (ContextCompat.checkSelfPermission(requireContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission), 101)
            } else {
                pickImageLauncher.launch("image/*")
            }
        }

        binding.buttonSave.setOnClickListener {
            val name = binding.editTextUsername.text.toString().trim()
            val surname = binding.editTextUserSurname.text.toString().trim()
            val tel = binding.editTextUserTelefon.text.toString().trim()
            val address = binding.editTextUserAddress.text.toString().trim()

            if (name.isEmpty() || surname.isEmpty() || tel.isEmpty() || address.isEmpty()) {
                Toast.makeText(requireContext(), "Lütfen tüm alanları doldurunuz.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = Users(
                name = name,
                surname = surname,
                telephone = tel,
                address = address,
                profileImgUrl = base64Image ?: ""
            )

            viewModel.saveUserToFirestore(user) { success, error ->
                if (success) {
                    Toast.makeText(requireContext(), "Profil başarıyla kaydedildi!", Toast.LENGTH_SHORT).show()
                    Navigation.findNavController(it).navigate(R.id.profilToAnasayfaGecis)
                } else {
                    Toast.makeText(requireContext(), "Hata: $error", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return binding.root
    }
}
