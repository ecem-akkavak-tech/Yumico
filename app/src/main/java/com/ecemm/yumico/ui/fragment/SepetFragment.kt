package com.ecemm.yumico.ui.fragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.ecemm.yumico.R
import com.ecemm.yumico.data.entity.YemekSepeti
import com.ecemm.yumico.databinding.FragmentSepetBinding
import com.ecemm.yumico.ui.adapter.SepetAdapter
import com.ecemm.yumico.ui.viewmodel.AnasayfaViewModel
import com.ecemm.yumico.ui.viewmodel.SepetViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SepetFragment : Fragment() {

    private lateinit var binding: FragmentSepetBinding

    // TODO-1-:  VIEW MODEL BAĞLAMA İŞLEMİ (fragmentlarda)
    private lateinit var viewModel: SepetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // TODO: dataBinding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sepet , container, false)

        
        binding.imageViewCloseSepet.setOnClickListener {
            Navigation.findNavController(it).navigate(R.id.anasayfaFragment)
        }

        //TODO: Veriyi alan taraftayız,bu yüzden **args**  && xml ve fragment tarafındaki nesneler eşleşir **/
        val bundle:SepetFragmentArgs by navArgs()
        val gelenYemekSepeti = bundle.yemekSepeti


        //TODO- Eğer aynı yemek zaten varsa sadece yemek adetini güncelle

        gelenYemekSepeti?.let { yemek ->
         /* LiveData’daki mevcut listeyi al.
            Eğer liste varsa onu değiştirilebilir (tuMutableList) hâle getir, yoksa boş bir değiştirilebilir liste (mutableListOf) oluştur.
         */
          val mevcutSepetList = viewModel.sepetListesi.value?.toMutableList() ?: mutableListOf()

          val mevcutYemek = mevcutSepetList.find{it.yemek_adi == yemek.yemek_adi}

          if (mevcutYemek != null) {
              // Eğer yemek zaten varsa adeti artır
              mevcutYemek.yemek_siparis_adet += gelenYemekSepeti.yemek_siparis_adet
          }else {
              mevcutSepetList.add(gelenYemekSepeti)
          }

            //livedata güncellenir
            viewModel.sepetListesi.value = mevcutSepetList
        }



        //TODO- adapter & recyclerview arası veri aktarma işlemi & liste gönderimi
        //TODO-RecyclerView’i önce boş bir adapter ile başlat, sonra LiveData geldiğinde veriyi güncelle.
        val sepetAdapter = SepetAdapter(requireContext(), listOf(), viewModel)
        binding.recyclerViewSepet.adapter = sepetAdapter


        viewModel.sepetListesi.observe(viewLifecycleOwner){ list ->
            sepetAdapter.sepettekiYemeklerListesi = list
            sepetAdapter.notifyDataSetChanged()
        }


        return binding.root
    }

    // TODO-2:  VIEW MODEL için gerekli (fragmentlarda)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel:SepetViewModel by viewModels()
        viewModel = tempViewModel
    }

    //TODO-3-: GÜNCEL LİSTE İÇİN
    override fun onResume() {
        //ekleme yaptıktan sonra **bu sayfaya geri döndüğümüzde** güncel sepetteki yemek listesini görmemizi sağlar
        super.onResume()
        getSepettekiYemek()//böylece sepet sayfasına döndüğümüz anda veriler tekrar yüklenmiş olacak
    }

    //TODO- Sepetteki Tüm yemekleri  getir
    fun getSepettekiYemek(){
        val bundle:SepetFragmentArgs by navArgs()
        val kullaniciAdi= bundle.yemekSepeti?.kullanici_adi ?: "Ecem Akkavak"
        viewModel.sepettekiYemekleriGetir(kullaniciAdi)
    }


}