package com.ecemm.yumico.ui.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ecemm.yumico.R
import com.ecemm.yumico.data.entity.Yemekler
import com.ecemm.yumico.databinding.CardDesignBinding
import com.ecemm.yumico.ui.fragment.AnasayfaFragmentDirections
import com.ecemm.yumico.ui.viewmodel.AnasayfaViewModel

class YemeklerAdapter(
    private val mContext: Context,
    private var yemeklerList: List<Yemekler>,
    private val viewModel: AnasayfaViewModel
) : RecyclerView.Adapter<YemeklerAdapter.CardDesignHolder>() {

    // UI anlık favori durumunu tutar
    private val favoriSet = mutableSetOf<Int>()

    inner class CardDesignHolder(var cardBinding: CardDesignBinding) :
        RecyclerView.ViewHolder(cardBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDesignHolder {
        val binding: CardDesignBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            R.layout.card_design,
            parent,
            false
        )
        return CardDesignHolder(binding)
    }

    override fun onBindViewHolder(holder: CardDesignHolder, position: Int) {
        val cBinding = holder.cardBinding
        val yemek = yemeklerList.get(position)

        cBinding.yemekObj = yemek

        // Glide ile resim yükleme
        val imgUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}"
        Glide.with(mContext)
            .load(imgUrl)
            .override(500, 700)
            .into(cBinding.imageViewYemekImg)

        // Card click → detay sayfası
        cBinding.cardViewYemekler.setOnClickListener { view ->
            val gecis = AnasayfaFragmentDirections.urunDetayGecis(yemek)
            Navigation.findNavController(view).navigate(gecis)
        }

        // Favori iconu güncelle
        val isFavori = favoriSet.contains(yemek.yemek_id)
        cBinding.imageViewFav.setImageResource(
            if (isFavori) R.drawable.favfill_img else R.drawable.favblank_img
        )

        // Favori click → AnasayfaViewModel üzerinden Firestore güncelle
        cBinding.imageViewFav.setOnClickListener {
            if (favoriSet.contains(yemek.yemek_id)) {
                favoriSet.remove(yemek.yemek_id)
                viewModel.favoriSil(yemek)   // Firestore’dan sil
            } else {
                favoriSet.add(yemek.yemek_id)
                viewModel.favoriEkle(yemek)  // Firestore’a ekle
            }
            notifyItemChanged(position) // UI güncelle
        }
    }

    override fun getItemCount(): Int = yemeklerList.size

    // Yeni yemek listesi geldiğinde set et
    fun updateYemekList(newList: List<Yemekler>) {
        yemeklerList = newList
        notifyDataSetChanged()
    }

    // Firestore’dan gelen favori id setini UI ile eşle
    fun updateFavoriSet(newFavoriSet: Set<Int>) {
        favoriSet.clear()
        favoriSet.addAll(newFavoriSet)
        notifyDataSetChanged()
    }
}

