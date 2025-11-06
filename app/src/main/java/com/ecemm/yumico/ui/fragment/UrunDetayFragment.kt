package com.ecemm.yumico.ui.fragment
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RatingBar
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ecemm.yumico.R
import com.ecemm.yumico.data.entity.YemekSepeti
import com.ecemm.yumico.databinding.FragmentUrunDetayBinding
import com.ecemm.yumico.ui.viewmodel.FavoriViewModel
import com.ecemm.yumico.ui.viewmodel.RatingYemekViewModel
import com.ecemm.yumico.ui.viewmodel.UrunDetayViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class UrunDetayFragment : Fragment() {
    private lateinit var binding:FragmentUrunDetayBinding
    // TODO:  VIEW MODEL BAĞLAMA İŞLEMİ (fragmentlarda)
    private val viewModel: UrunDetayViewModel by activityViewModels() //ortak livedata olduğu için shared olmalı
    private val favoriViewModel: FavoriViewModel by activityViewModels()
    private val ratingYemekViewModel: RatingYemekViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // TODO: dataBinding kurulum
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_urun_detay , container, false)


        //TODO: Veriyi alan taraftayız,bu yüzden **args**  && xml ve fragment tarafındaki nesneler eşleşir **/
        val bundle:UrunDetayFragmentArgs by navArgs()
        val alinanYemek = bundle.yemek
        binding.yemekObject = alinanYemek //xml ve fragment tarafındaki nesneler eşleşir



        //TODO- RATINGBAR - (create & observe livedata)
        binding.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            ratingYemekViewModel.ratingEkle(alinanYemek.yemek_id,alinanYemek.yemek_adi ,rating)
            Log.e("RATING: ", rating.toString())
        }
        //değişikliğin sayfadan çıkılıp tekrar girilse bile yansıması için
        ratingYemekViewModel.ratingGetir(alinanYemek.yemek_id)

        ratingYemekViewModel.ratingBarValue.observe(viewLifecycleOwner){ ratingBar ->
              binding.ratingBar.rating =ratingBar
       }



        /*TODO- retrofit & glide ile internete yüklenen resmi alma  */
        val imgUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${alinanYemek.yemek_resim_adi}"
        Glide.with(requireContext())
            .load(imgUrl)
            .override(500,700)
            .into(binding.imageViewYemekImg)


        //todo- ürün adedi işlemi
        binding.urunAdet = 0
        binding.btnAzalt.setOnClickListener {
            if(binding.urunAdet==0){
                AlertDialog.Builder(requireContext())
                    .setTitle("Uyarı")
                    .setMessage("Ürün adedi 0'dan daha az olamaz!")
                    .setPositiveButton("Tamam") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
                binding.urunAdet = 0
            }
           else{
                binding.urunAdet -= 1
            }
        }

        binding.btnArttir.setOnClickListener {
            if(binding.urunAdet ==30){
                AlertDialog.Builder(requireContext())
                    .setTitle("Uyarı")
                    .setMessage("En fazla 30 adet aynı üründen seçebilirsiniz ")
                    .setPositiveButton("Tamam") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
                binding.urunAdet = 30
            }else{
                binding.urunAdet += 1
            }
        }

        //todo: bu sayfadan sepet butonuna tıkladığımız anda SepetFragment'a veri göndericez
        //todo: UrunDetayFragment sadece “sipariş” iletiyor.
        // UrunDetayFragment-> **directions**    &&    SepetFragment-> **args

        binding.buttonSepeteEkle.setOnClickListener {
            if(binding.urunAdet > 0){
                Snackbar.make(it,
                    " \"${alinanYemek.yemek_adi}\" sepete eklendi. (adet: ${binding.urunAdet}) ",
                    Snackbar.LENGTH_SHORT
                ).show()

              yemekEkle(
                  alinanYemek.yemek_adi,
                  alinanYemek.yemek_resim_adi,
                  alinanYemek.yemek_fiyat,
                  binding.urunAdet,
                  "Ecem"
              )

                /* todo not: hata almamak için activity_main_nav kısmında sepetFragment içindeki yemek entitysi nullable yapılmalı */
            }
        }

        //todo- favorilere ekleme & kaldırma için viewmodeldeki listeyi kullandığımızdan observe lazım
        favoriViewModel.favoriListesi.observe(viewLifecycleOwner){favList->

            if(favList?.any { it.yemek_adi == alinanYemek.yemek_adi } == true){
                binding.imageViewFavori.setImageResource(R.drawable.favfill_img)
            }else {
                binding.imageViewFavori.setImageResource(R.drawable.favblank_img)
            }

        }

        binding.imageViewFavori.setOnClickListener {
            val favoriYemek = favoriViewModel.favoriListesi.value?.find { it.yemek_adi == alinanYemek.yemek_adi }

            if (favoriYemek != null) {
                // Eğer favoride varsa, gerçek ID ile sil
                favoriViewModel.favoriSil(favoriYemek.yemek_id)
            } else {
                // Yoksa ekle
                favoriViewModel.favoriEkle(alinanYemek.yemek_adi, alinanYemek.yemek_resim_adi, alinanYemek.yemek_fiyat)

            }

        }

        binding.buttonCloseUrun.setOnClickListener {
            findNavController().popBackStack() //1 önceki fragmenta gider
        }

        return binding.root
    }

    // TODO:  VIEW MODEL için gerekli (fragmentlarda)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    //TODO- Sepete yemek ekle - post
    fun yemekEkle(yemekAdi:String,yemekResimAdi:String,yemekFiyat:Int,yemekSiparisAdet:Int,kullaniciAdi:String){
        viewModel.sepeteYemekEkle(yemekAdi,yemekResimAdi,yemekFiyat,yemekSiparisAdet,kullaniciAdi)
        val gecis = UrunDetayFragmentDirections.sepetGecis(
            yemekSepeti = YemekSepeti(0,yemekAdi,yemekResimAdi,yemekFiyat,yemekSiparisAdet,kullaniciAdi)
        )
        Navigation.findNavController(binding.buttonSepeteEkle).navigate(gecis)
    }
}