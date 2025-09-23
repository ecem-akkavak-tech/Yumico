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
import com.ecemm.yumico.databinding.FragmentSepetBinding
import com.ecemm.yumico.ui.adapter.SepetAdapter

class SepetFragment : Fragment() {
    private lateinit var binding: FragmentSepetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // TODO: dataBinding kurulum
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sepet , container, false)

        //TODO: Veriyi alan taraftayız,bu yüzden **args**  && xml ve fragment tarafındaki nesneler eşleşir **/
        val bundle:SepetFragmentArgs by navArgs()
        var sepeteEklenenYemek = bundle.yemek
        val yemekList = mutableListOf(sepeteEklenenYemek)
        val yemekAdet =bundle.adet

        //TODO- YEMEKSEPETİ LİSTESİ
        val yemekSepetiList = mutableListOf<YemekSepeti>()


        yemekSepetiList.add(YemekSepeti(yemekList,yemekAdet))




        val sepetAdapter = SepetAdapter(requireContext(),yemekSepetiList)
        binding.sepetAdapter = sepetAdapter //adapterlar eşleştirilir
        sepetAdapter.notifyDataSetChanged()
        return binding.root
    }


}