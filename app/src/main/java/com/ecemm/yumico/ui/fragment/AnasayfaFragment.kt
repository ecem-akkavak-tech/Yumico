package com.ecemm.yumico.ui.fragment
import LoadingDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AutoCompleteTextView
import android.widget.EditText
import androidx.databinding.DataBindingUtil
import android.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ecemm.yumico.R
import com.ecemm.yumico.databinding.FragmentAnasayfaBinding
import com.ecemm.yumico.ui.adapter.YemeklerAdapter
import com.ecemm.yumico.ui.viewmodel.AnasayfaViewModel
import com.ecemm.yumico.ui.viewmodel.FavoriViewModel
import com.ecemm.yumico.ui.viewmodel.auth.AuthViewModel
import com.ecemm.yumico.utils.SiralamaTuru
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnasayfaFragment : Fragment() {
   private lateinit var binding: FragmentAnasayfaBinding
   private lateinit var loadingDialog: LoadingDialog
   private var aramaKelimesi:String = ""
   private var siralamaTuru:SiralamaTuru = SiralamaTuru.DEFAULT

    // TODO-1-:  VIEW MODEL BAĞLAMA İŞLEMİ (fragmentlarda)
    private val viewModel: AnasayfaViewModel  by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()
    private val favoriViewModel: FavoriViewModel by activityViewModels() //ortak livedata olduğu için shared olmalı (icon ile ekle-sil)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        loadingDialog = LoadingDialog(requireContext())

        //todo- dataBinding kurulum
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_anasayfa , container, false)
        binding.anasayfaObject = this
        binding.recyclerviewYemekler.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


        authViewModel.getUserFromFirestore() { user->
            binding.toolbarAnasayfa.title = "Hoşgeldin ${user?.name ?: "Misafir"}"
        }


        //todo- SEARCH VIEW içini doldur ********/
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String?): Boolean {
                //search view'a harf girdikçe veya sildikçe bize sonuç döndüren fonksiyondur
                aramaKelimesi = newText ?: ""
                filtrelemeYap(aramaKelimesi, siralamaTuru)
                return true
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                //search view'a harf girilme işlemini yaptıktan sonra arama iconuna tıkladığımızda bize sonuç döndüren fonksiyondur
                aramaKelimesi = query ?: ""
                if(aramaKelimesi.isBlank()){
                    viewModel.yemekleriGetir()
                }
                filtrelemeYap(aramaKelimesi, siralamaTuru)
                return true
            }
        })

        binding.imageViewFiyatArtan.setOnClickListener {
           siralamaTuru = SiralamaTuru.ARTAN
           filtrelemeYap(aramaKelimesi, siralamaTuru)
        }

        binding.imageViewFiyatAzalan.setOnClickListener {
            siralamaTuru = SiralamaTuru.AZALAN
            filtrelemeYap(aramaKelimesi, siralamaTuru)
        }


        //TODO- adapter & recyclerview arası veri aktarma işlemi & liste gönderimi
        //TODO-RecyclerView’i önce boş bir adapter ile başlat, sonra LiveData geldiğinde veriyi güncelle.
        val yemeklerAdapter = YemeklerAdapter(requireContext(), listOf() ,favoriViewModel,authViewModel)
        binding.recyclerviewYemekler.adapter = yemeklerAdapter

        //TODO- LIVE DATA OBSERVE
        viewModel.yemeklerListesi.observe(viewLifecycleOwner){ yemekListesi ->
            loadingDialog.hide() //livedata gelmeden hemen önce kapat
            yemeklerAdapter.yemeklerList = yemekListesi
            yemeklerAdapter.notifyDataSetChanged()
        }

       

        return binding.root
    }


    //TODO-3-: GÜNCEL LİSTE İÇİN
    override fun onResume() {
        super.onResume()
        loadingDialog.show()       // loadingi başlat
        viewModel.yemekleriGetir() //böylece anasayfaya döndüğümüz anda veriler tekrar yüklenmiş olacak

    }

   fun filtrelemeYap(aramaKelimesi:String,siralamaTuru:SiralamaTuru){
       viewModel.yemekFiltresiyleAra(aramaKelimesi, siralamaTuru)
   }
}