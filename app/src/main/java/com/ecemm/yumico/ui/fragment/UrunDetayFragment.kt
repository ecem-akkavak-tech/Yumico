package com.ecemm.yumico.ui.fragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.ecemm.yumico.R
import com.ecemm.yumico.databinding.FragmentUrunDetayBinding

class UrunDetayFragment : Fragment() {
    private lateinit var binding:FragmentUrunDetayBinding
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

        //todo:resmi almak için (sonra sil )
        val imageId = requireContext().resources.getIdentifier(alinanYemek.yemek_resim_adi, "drawable", requireContext().packageName)
        if (imageId != 0) {
            binding.imageViewYemekImg.setImageResource(imageId)
        } else {
            binding.imageViewYemekImg.setImageResource(R.drawable.favblank_img)
        }

        //todo- asıl gereken glide ile resim alma
//        val imgUrl = "http://kasimadalan.pe.hu/filmler_yeni/resimler/${film.resim}"
//        Glide.with(this)
//            .load(imgUrl)
//            .override(500,700)
//            .into(binding.imageViewFilm)

        //todo- ürün adedi işlemi

        binding.urunAdet=0

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



        return binding.root
    }



}