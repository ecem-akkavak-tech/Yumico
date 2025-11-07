package com.ecemm.yumico.ui.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ecemm.yumico.R
import com.ecemm.yumico.data.entity.FavoriYemek
import com.ecemm.yumico.databinding.FavoriCardDesignBinding
import com.ecemm.yumico.ui.viewmodel.FavoriViewModel


class FavoriAdapter(
    var mContext: Context,
    var favoriYemekList:List<FavoriYemek>,
    var favoriViewModel: FavoriViewModel,
): RecyclerView.Adapter<FavoriAdapter.CardDesignHolder>(){

    inner class CardDesignHolder(var cardBinding: FavoriCardDesignBinding) : RecyclerView.ViewHolder(cardBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDesignHolder {
        // todo: data binding işlemi**/
        val binding: FavoriCardDesignBinding = DataBindingUtil.inflate<FavoriCardDesignBinding>(
            LayoutInflater.from(mContext),
            R.layout.favori_card_design,
            parent,
            false
        )
        return CardDesignHolder(binding)
    }

    override fun onBindViewHolder(holder: CardDesignHolder, position: Int) {
        /** card View ile ilgili tüm işlemler (tıklama vs) burada olacak
         * 1-holder nesnesi sayesinde CardDesignHolder classındaki  cardBinding'e ulaşıcaz
         * 2-position ise bir döngünün indexi gibi düşün (her 1 nesneye teker teker ulaşacak) **/
        val cBinding = holder.cardBinding
        val favoriYemek = favoriYemekList.get(position)
        cBinding.favoriYemekObj = favoriYemek

        //favori yemek resmi retrofit ile geldiğinden glide:
        val resimUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${favoriYemek.yemek_resim_adi}"
        Glide.with(mContext).load(resimUrl).into(cBinding.imageViewFavYemekImage)


        //TODO- Rating değerini güncelle
        cBinding.ratingBarFavori.rating = favoriYemek.rating
        cBinding.textViewRating.text = favoriYemek.rating.toString()
        cBinding.ratingBarFavori.setOnRatingBarChangeListener { _, rating, _ ->
            favoriViewModel.favoriRatingGuncelle(favoriYemek.yemek_id, rating)
        }
    }

    override fun getItemCount(): Int {
        return favoriYemekList.size
    }

}