package com.ecemm.yumico.ui.fragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecemm.yumico.R
import com.ecemm.yumico.databinding.FragmentFavorilerBinding
import com.ecemm.yumico.ui.adapter.FavoriAdapter
import com.ecemm.yumico.ui.viewmodel.FavoriViewModel
import com.ecemm.yumico.ui.viewmodel.RatingYemekViewModel
import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class FavorilerFragment : Fragment() {
    private lateinit var binding:FragmentFavorilerBinding
    private lateinit var favoriViewModel: FavoriViewModel
    private lateinit var ratingYemekViewModel:RatingYemekViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        //todo- dataBinding kurulum
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favoriler , container, false)
        binding.favorilerObject = this
        binding.recyclerViewFavoriler.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)


        /*
        TODO
           - adapter & recyclerview arası veri aktarma işlemi & liste gönderimi
           - RecyclerView’i önce boş bir adapter ile başlat, sonra LiveData geldiğinde veriyi güncelle.
           - UI management (Manage Favori Fill & Blank)
           - Rating Yemek güncelleme
        */

        val favoriAdapter = FavoriAdapter(requireContext(), listOf(), favoriViewModel , ratingYemekViewModel,viewLifecycleOwner)
        binding.recyclerViewFavoriler.adapter = favoriAdapter

        favoriViewModel.favoriListesi.observe(viewLifecycleOwner){ liste ->
            if(liste.isNullOrEmpty()){
                binding.manageFavoriBlank.visibility = View.VISIBLE
                binding.manageFavoriFill.visibility = View.GONE
            }else{
                binding.manageFavoriBlank.visibility = View.GONE
                binding.manageFavoriFill.visibility = View.VISIBLE
            }

            favoriAdapter.favoriYemekList = liste
            favoriAdapter.notifyDataSetChanged()
        }

        return binding.root
    }

    //TODO:  VIEW MODEL İÇİN
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel : FavoriViewModel by viewModels()
        favoriViewModel = tempViewModel

        val tempRatingViewModel : RatingYemekViewModel by viewModels()
        ratingYemekViewModel = tempRatingViewModel
    }


    //TODO-3-: GÜNCEL LİSTE İÇİN
    override fun onResume() {
        //ekleme yaptıktan sonra **bu sayfaya geri döndüğümüzde** güncel favori listesini görmemizi sağlar
        super.onResume()
        favoriViewModel.favoriYemekleriGetir()

    }


}