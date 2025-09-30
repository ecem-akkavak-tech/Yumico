package com.ecemm.yumico.ui.fragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.ecemm.yumico.R
import com.ecemm.yumico.data.entity.YemekSepeti
import com.ecemm.yumico.data.entity.Yemekler
import com.ecemm.yumico.databinding.FragmentAnasayfaBinding
import com.ecemm.yumico.databinding.FragmentFavorilerBinding
import com.ecemm.yumico.ui.adapter.FavoriAdapter
import com.ecemm.yumico.ui.adapter.YemeklerAdapter

class FavorilerFragment : Fragment() {

    private val favoriListesi = mutableListOf<Yemekler>() //bir canlı liste göstereceğimizden mutable

    private lateinit var binding:FragmentFavorilerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFavorilerBinding.inflate(inflater, container, false) //viewbinding

        //todo- dataBinding kurulum
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favoriler , container, false)
        binding.favorilerObject = this



        //TODO- Eğer aynı yemek zaten varsa favorilere ekleme yapma
//        val newList = favoriListesi.toMutableList()

//        gelenYemek?.let { yemek ->
//            // burası sadece gelenYemek null değilse çalışır
//            val mevcutYemek = newList.find { it.yemek.yemek_id == gelenYemek.yemek_id }
//            if (mevcutYemek != null) {
//                // Eğer yemek zaten varsa adeti artır
//                mevcutYemek.yemekAdet += gelenAdet
//            } else {
//                newList.add(YemekSepeti(gelenYemek, gelenAdet))
//            }
//        }
//
//        sepetListesi.clear()
//        sepetListesi.addAll(newList)


        //adapter & recyclerview arası veri aktarma işlemi
        val favoriAdapter = FavoriAdapter(requireContext(),favoriListesi)
        binding.recyclerViewFavoriler.adapter = favoriAdapter









        return binding.root
    }



}