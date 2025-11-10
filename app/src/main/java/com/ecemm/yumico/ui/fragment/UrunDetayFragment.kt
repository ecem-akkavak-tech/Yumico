package com.ecemm.yumico.ui.fragment
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ecemm.yumico.R
import com.ecemm.yumico.data.entity.YemekSepeti
import com.ecemm.yumico.databinding.FragmentUrunDetayBinding
import com.ecemm.yumico.ui.viewmodel.FavoriViewModel
import com.ecemm.yumico.ui.viewmodel.UrunDetayViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UrunDetayFragment : Fragment() {

    private lateinit var binding: FragmentUrunDetayBinding
    private val viewModel: UrunDetayViewModel by activityViewModels()
    private val favoriViewModel: FavoriViewModel by activityViewModels()
    private val args: UrunDetayFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_urun_detay, container, false)
        val alinanYemek = args.yemek
        binding.yemekObject = alinanYemek
        binding.urunAdet = 0

        // Glide ile resim yükleme
        Glide.with(requireContext())
            .load("http://kasimadalan.pe.hu/yemekler/resimler/${alinanYemek.yemek_resim_adi}")
            .override(500, 700)
            .into(binding.imageViewYemekImg)

        // Ürün adedi işlemleri
        binding.btnAzalt.setOnClickListener {
            if (binding.urunAdet == 0) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Uyarı")
                    .setMessage("Ürün adedi 0'dan az olamaz!")
                    .setPositiveButton("Tamam") { dialog, _ -> dialog.dismiss() }
                    .show()
            } else binding.urunAdet -= 1
        }

        binding.btnArttir.setOnClickListener {
            if (binding.urunAdet == 30) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Uyarı")
                    .setMessage("En fazla 30 adet seçebilirsiniz!")
                    .setPositiveButton("Tamam") { dialog, _ -> dialog.dismiss() }
                    .show()
            } else binding.urunAdet += 1
        }

        // Sepete ekleme
        binding.buttonSepeteEkle.setOnClickListener {
            if (binding.urunAdet > 0) {
                Snackbar.make(
                    it,
                    "\"${alinanYemek.yemek_adi}\" sepete eklendi. (adet: ${binding.urunAdet})",
                    Snackbar.LENGTH_SHORT
                ).show()

                viewModel.sepeteYemekEkle(
                    alinanYemek.yemek_adi,
                    alinanYemek.yemek_resim_adi,
                    alinanYemek.yemek_fiyat,
                    binding.urunAdet,
                    "Ecem"
                )

                val gecis = UrunDetayFragmentDirections.sepetGecis(
                    YemekSepeti(
                        0,
                        alinanYemek.yemek_adi,
                        alinanYemek.yemek_resim_adi,
                        alinanYemek.yemek_fiyat,
                        binding.urunAdet,
                        "Ecem"
                    )
                )
                findNavController().navigate(gecis)
            }
        }

        // Favori ve Rating güncelleme tek observer ile
        favoriViewModel.favoriListesi.observe(viewLifecycleOwner) { favList ->
            val favoriYemek = favList.find { it.yemek_adi == alinanYemek.yemek_adi }

            // Favori ikon ve rating güncelle
            if (favoriYemek != null) {
                binding.imageViewFavori.setImageResource(R.drawable.favfill_img)
                binding.ratingBar.rating = favoriYemek.rating
            } else {
                binding.imageViewFavori.setImageResource(R.drawable.favblank_img)
                binding.ratingBar.rating = 0f
            }

            // RatingBar listener
            binding.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
                if (favoriYemek != null) {
                    favoriViewModel.favoriRatingGuncelle(favoriYemek.yemek_id, rating)
                }
            }
        }

        // Favori ekle/kaldır
        binding.imageViewFavori.setOnClickListener {
            val favoriYemek = favoriViewModel.favoriListesi.value?.find { it.yemek_adi == alinanYemek.yemek_adi }
            val currentRating = binding.ratingBar.rating

            if (favoriYemek != null) {
                favoriViewModel.favoriSil(favoriYemek.yemek_id)
            } else {
                favoriViewModel.favoriEkle(
                    alinanYemek.yemek_adi,
                    alinanYemek.yemek_resim_adi,
                    alinanYemek.yemek_fiyat,
                    currentRating
                )
            }
        }

        // Close butonu
        binding.buttonCloseUrun.setOnClickListener {
            findNavController().popBackStack()
        }

        return binding.root
    }
}
