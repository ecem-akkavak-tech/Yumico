package com.ecemm.yumico.ui.fragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ecemm.yumico.R
import com.ecemm.yumico.data.entity.YemekSepeti
import com.ecemm.yumico.databinding.FragmentSepetBinding
import com.ecemm.yumico.ui.adapter.SepetAdapter
class SepetFragment : Fragment() {

    private val sepetListesi = mutableListOf<YemekSepeti>() //

    private lateinit var binding: FragmentSepetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // TODO: dataBinding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sepet , container, false)

        //TODO: Veriyi alan taraftayız,bu yüzden **args**  && xml ve fragment tarafındaki nesneler eşleşir **/
        val bundle:SepetFragmentArgs by navArgs()
        val gelenYemek = bundle.yemek
        val gelenAdet =bundle.adet

        //TODO- Eğer aynı yemek zaten varsa sadece yemek adetini güncelle
        val newList = sepetListesi.toMutableList()

        gelenYemek?.let { yemek ->
            // burası sadece gelenYemek null değilse çalışır
            val mevcutYemek = newList.find { it.yemek.yemek_id == gelenYemek.yemek_id }
            if (mevcutYemek != null) {
                // Eğer yemek zaten varsa adeti artır
                mevcutYemek.yemekAdet += gelenAdet
            } else {
                newList.add(YemekSepeti(gelenYemek, gelenAdet))
            }
        }

        sepetListesi.clear()
        sepetListesi.addAll(newList)

        val sepetAdapter = SepetAdapter(requireContext(),sepetListesi)
        binding.recyclerViewSepet.adapter = sepetAdapter //adapterlar eşleştirilir
        sepetAdapter.notifyDataSetChanged()



        binding.imageViewCloseSepet.setOnClickListener {
            findNavController().popBackStack(R.id.urunDetayFragment, false)
            //urunDetayFragment geçiş yapar
        }
        return binding.root
    }


}