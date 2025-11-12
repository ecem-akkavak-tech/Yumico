package com.ecemm.yumico.ui.fragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import android.widget.SearchView
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ecemm.yumico.R
import com.ecemm.yumico.databinding.FragmentAnasayfaBinding
import com.ecemm.yumico.ui.adapter.YemeklerAdapter
import com.ecemm.yumico.ui.viewmodel.AnasayfaViewModel
import com.ecemm.yumico.ui.viewmodel.FavoriViewModel
import com.ecemm.yumico.ui.viewmodel.auth.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnasayfaFragment : Fragment() {
   private lateinit var binding: FragmentAnasayfaBinding

    // TODO-1-:  VIEW MODEL BAĞLAMA İŞLEMİ (fragmentlarda)
    private val viewModel: AnasayfaViewModel  by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()
    private val favoriViewModel: FavoriViewModel by activityViewModels() //ortak livedata olduğu için shared olmalı (icon ile ekle-sil)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        //todo- dataBinding kurulum
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_anasayfa , container, false)
        binding.anasayfaObject = this
        binding.recyclerviewYemekler.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)


        //todo- SEARCH VIEW içini doldur ********/
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextChange(newText: String?): Boolean {
                //search view'a harf girdikçe veya sildikçe bize sonuç döndüren fonksiyondur
                viewModel.yemekFiltresiyleAra(newText ?: "")
                return true
            }
            override fun onQueryTextSubmit(query: String?): Boolean {
                //search view'a harf girilme işlemini yaptıktan sonra arama iconuna tıkladığımızda bize sonuç döndüren fonksiyondur
                viewModel.yemekFiltresiyleAra(query ?: "")
                return true
            }
        })


        //TODO- adapter & recyclerview arası veri aktarma işlemi & liste gönderimi
        //TODO-RecyclerView’i önce boş bir adapter ile başlat, sonra LiveData geldiğinde veriyi güncelle.
        val yemeklerAdapter = YemeklerAdapter(requireContext(), listOf() ,favoriViewModel)
        binding.recyclerviewYemekler.adapter = yemeklerAdapter

        //TODO- LIVE DATA OBSERVE
        //yemek listesi live data
        viewModel.yemeklerListesi.observe(viewLifecycleOwner){ yemekListesi ->
            yemeklerAdapter.yemeklerList = yemekListesi
            yemeklerAdapter.notifyDataSetChanged()
        }





        // TODO-  NavHostFragment + BottomNavigationView safe setup
        val navHostFragment = requireActivity()
                              .supportFragmentManager
                              .findFragmentById(R.id.navHostFragment) as NavHostFragment
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            val navController = navHostFragment.navController
            // duplicate navigate engelle
            if (item.itemId != navController.currentDestination?.id) {
                NavigationUI.onNavDestinationSelected(item, navController)
            }
            true
        }

        // Geri tuşu davranışı
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            val navController = navHostFragment.navController
            val currentFragmentId = navController.currentDestination?.id
            if (currentFragmentId == R.id.anasayfaFragment) {
                requireActivity().finish() // AnaSayfa'da uygulamayı kapat
            } else {
                navController.popBackStack()
            }
        }


        authViewModel.getUserFromFirestore() { user->
            binding.toolbarAnasayfa.title = "Hoşgeldin ${user?.name ?: "Misafir"}"
        }

        return binding.root
    }


    //TODO-3-: GÜNCEL LİSTE İÇİN
    override fun onResume() {
        //ekleme yaptıktan sonra **bu sayfaya geri döndüğümüzde** güncel yemekler listesini görmemizi sağlar
        super.onResume()
        viewModel.yemekleriGetir() //böylece anasayfaya döndüğümüz anda veriler tekrar yüklenmiş olacak

    }

}