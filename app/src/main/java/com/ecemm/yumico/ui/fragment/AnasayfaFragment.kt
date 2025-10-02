package com.ecemm.yumico.ui.fragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import android.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ecemm.yumico.R
import com.ecemm.yumico.data.entity.Yemekler
import com.ecemm.yumico.databinding.FragmentAnasayfaBinding
import com.ecemm.yumico.ui.adapter.YemeklerAdapter
import com.ecemm.yumico.ui.viewmodel.AnasayfaViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AnasayfaFragment : Fragment() {
   private lateinit var binding: FragmentAnasayfaBinding

    // TODO-1-:  VIEW MODEL BAĞLAMA İŞLEMİ (fragmentlarda)
    private lateinit var viewModel: AnasayfaViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentAnasayfaBinding.inflate(inflater, container, false) //viewbinding

        //todo- dataBinding kurulum
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_anasayfa , container, false)
        binding.anasayfaObject = this
        binding.tbAnasayfaTitle = "Hoşgeldin,"
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
        val yemeklerAdapter = YemeklerAdapter(requireContext(), listOf(), viewModel)
        binding.recyclerviewYemekler.adapter = yemeklerAdapter

        viewModel.yemeklerListesi.observe(viewLifecycleOwner){ yemekListesi ->
            yemeklerAdapter.yemeklerList = yemekListesi
            yemeklerAdapter.notifyDataSetChanged()
            /* ESKİ EKSİK USUL:
               val kisilerAdapterr = KisilerAdapter(requireContext(),it,viewModel)
               binding.kisilerAdapter = kisilerAdapterr
             */
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

        return binding.root
    }

    // TODO-2-:  VIEW MODEL için gerekli (fragmentlarda)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel:AnasayfaViewModel by viewModels()
        viewModel = tempViewModel
    }

    //TODO-3-: GÜNCEL LİSTE İÇİN
    override fun onResume() {
        //ekleme yaptıktan sonra **bu sayfaya geri döndüğümüzde** güncel yemekler listesini görmemizi sağlar
        super.onResume()
        viewModel.yemekleriGetir() //böylece anasayfaya döndüğümüz anda veriler tekrar yüklenmiş olacak

    }

}