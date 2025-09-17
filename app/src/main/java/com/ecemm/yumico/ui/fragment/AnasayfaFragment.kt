package com.ecemm.yumico.ui.fragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ecemm.yumico.data.entity.Yemekler
import com.ecemm.yumico.databinding.FragmentAnasayfaBinding
import com.ecemm.yumico.ui.adapter.YemeklerAdapter

class AnasayfaFragment : Fragment() {
   private lateinit var binding: FragmentAnasayfaBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAnasayfaBinding.inflate(inflater, container, false) //viewbinding


        //todo- dataBinding kurulum
        //binding = DataBindingUtil.inflate(inflater, R.layout.fragment_homepage , container, false)
        //binding.homepageObject = this
        //binding.toolbarHomepageTitle = "Yemekler"


       //todo- geçici verilerle recyclerview
        //binding.recyclerviewYemekler.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerviewYemekler.layoutManager = StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        val yemekListesi = ArrayList<Yemekler>()
        val yemek1 = Yemekler(1,"Mantı","manti_img.jpg",250)
        val yemek2 = Yemekler(2,"Baklava","baklava_img.jpg",300)
        val yemek3 = Yemekler(3,"Tortilla","tortilla_img.png",170)

        yemekListesi.add(yemek1)
        yemekListesi.add(yemek2)
        yemekListesi.add(yemek3)
        yemekListesi.add(yemek1)
        yemekListesi.add(yemek2)
        yemekListesi.add(yemek3)

        //adapter & recyclerview arası veri aktarma işlemi
        val yemeklerAdapter = YemeklerAdapter(requireContext(),yemekListesi)
        binding.recyclerviewYemekler.adapter = yemeklerAdapter


        return binding.root
    }


}