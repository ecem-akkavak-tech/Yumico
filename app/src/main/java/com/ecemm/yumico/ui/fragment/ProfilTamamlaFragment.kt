package com.ecemm.yumico.ui.fragment
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import com.ecemm.yumico.databinding.FragmentProfilTamamlaBinding
import com.ecemm.yumico.ui.viewmodel.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import android.app.Activity
import android.graphics.BitmapFactory
import android.util.Base64
import android.widget.Toast
import androidx.navigation.Navigation
import com.ecemm.yumico.R
import com.ecemm.yumico.data.entity.Users

@AndroidEntryPoint
class ProfilTamamlaFragment : Fragment() {
    private lateinit var binding: FragmentProfilTamamlaBinding
    private val viewModel: AuthViewModel by viewModels()
    private var base64Image: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProfilTamamlaBinding.inflate(inflater, container, false)

        // todo- Profil resmi seçimi (galeriden)
        binding.imageViewProfileImg.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, 100)
        }

        binding.buttonSave.setOnClickListener{
            val name = binding.editTextUsername.text.toString().trim()
            val surname = binding.editTextUserSurname.text.toString().trim()
            val tel = binding.editTextUserTelefon.text.toString().trim()
            val address = binding.editTextUserAddress.text.toString().trim()

            val user = Users(userId = "",
                name=name,
                surname=surname,
                telephone=tel,
                address=address,
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

    // todo- Seçilen resmi Base64’e çevir
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == AppCompatActivity.RESULT_OK && data != null) {
            val imageUri = data.data
            val inputStream = requireContext().contentResolver.openInputStream(imageUri!!)

            val bytes = inputStream?.readBytes()
            base64Image = Base64.encodeToString(bytes, Base64.DEFAULT)
            binding.imageViewProfileImg.setImageURI(imageUri)

        }
    }
}