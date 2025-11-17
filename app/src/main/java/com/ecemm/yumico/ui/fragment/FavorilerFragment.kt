package com.ecemm.yumico.ui.fragment
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecemm.yumico.R
import com.ecemm.yumico.databinding.FragmentFavorilerBinding
import com.ecemm.yumico.ui.adapter.FavoriAdapter
import com.ecemm.yumico.ui.viewmodel.FavoriViewModel
import com.ecemm.yumico.ui.viewmodel.SepetViewModel
import com.ecemm.yumico.ui.viewmodel.auth.AuthViewModel

import dagger.hilt.android.AndroidEntryPoint
@AndroidEntryPoint
class FavorilerFragment : Fragment() {
    private lateinit var binding:FragmentFavorilerBinding
    private val favoriViewModel: FavoriViewModel by activityViewModels()
    private val sepetViewModel:SepetViewModel by activityViewModels()
    private val authViewModel: AuthViewModel by activityViewModels()
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

        */

        val favoriAdapter = FavoriAdapter(requireContext(), listOf(), favoriViewModel ,sepetViewModel, authViewModel)
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


        // Close butonu
        binding.buttonCloseFavoriler.setOnClickListener {
            findNavController().popBackStack()
        }



        return binding.root
    }


    //TODO-3-: GÜNCEL LİSTE İÇİN
    override fun onResume() {
        //ekleme yaptıktan sonra **bu sayfaya geri döndüğümüzde** güncel favori listesini görmemizi sağlar
        super.onResume()
        favoriViewModel.favoriYemekleriGetir()
    }
}