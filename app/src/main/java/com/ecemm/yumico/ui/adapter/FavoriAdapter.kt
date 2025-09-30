package com.ecemm.yumico.ui.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ecemm.yumico.R
import com.ecemm.yumico.data.entity.Yemekler
import com.ecemm.yumico.databinding.CardDesignBinding
import com.ecemm.yumico.databinding.FavoriCardDesignBinding

class FavoriAdapter(var mContext: Context, var favoriYemekList:List<Yemekler>
) : RecyclerView.Adapter<FavoriAdapter.CardDesignHolder>(){
    inner class CardDesignHolder(var cardBinding: FavoriCardDesignBinding) : RecyclerView.ViewHolder(cardBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDesignHolder {
        //viewBinding kurulumu burada
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
        cBinding.favoriYemeklerObj = favoriYemek

        //todo:resmi almak için (sonra sil )
        val imageId = mContext.resources.getIdentifier(
            favoriYemek.yemek_resim_adi,
            "drawable",
            mContext.packageName
        )
        if (imageId != 0) {
            cBinding.imageViewYemekImage.setImageResource(imageId)
        } else {
            cBinding.imageViewYemekImage.setImageResource(R.drawable.favblank_img)
        }

    }

    override fun getItemCount(): Int {
        return favoriYemekList.size
    }
}