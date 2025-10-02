package com.ecemm.yumico.ui.fragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ecemm.yumico.R
import com.ecemm.yumico.data.entity.YemekSepeti
import com.ecemm.yumico.databinding.FragmentUrunDetayBinding
import com.google.android.material.snackbar.Snackbar

class UrunDetayFragment : Fragment() {
    private lateinit var binding:FragmentUrunDetayBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // TODO: dataBinding kurulum
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_urun_detay , container, false)

        binding.imageViewClose.setOnClickListener {
            findNavController().popBackStack(R.id.anasayfaFragment, false)
            //anasayfaya geçiş yapar ve anasayfadan geriye gidersek arkada bu sayfayı bırakmaz
        }
        //TODO: Veriyi alan taraftayız,bu yüzden **args**  && xml ve fragment tarafındaki nesneler eşleşir **/
        val bundle:UrunDetayFragmentArgs by navArgs()
        val alinanYemek = bundle.yemek
        binding.yemekObject = alinanYemek //xml ve fragment tarafındaki nesneler eşleşir


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

                val gecis = UrunDetayFragmentDirections.sepetGecis(
                    yemek=alinanYemek,
                    adet = binding.urunAdet
                )
                Navigation.findNavController(it).navigate(gecis)
                /* todo
                not: hata almamak için activity_main_nav kısmındaki sepetFragment içindeki yemek entitysi nullable yapılmalı */
            }

        }
        return binding.root
    }



}