package com.ecemm.yumico.ui.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ecemm.yumico.R
import com.ecemm.yumico.data.entity.Yemekler
import com.ecemm.yumico.databinding.CardDesignBinding
import com.ecemm.yumico.ui.fragment.AnasayfaFragmentDirections

class YemeklerAdapter(var mContext:Context , var yemeklerList:List<Yemekler>
) : RecyclerView.Adapter<YemeklerAdapter.CardDesignHolder>(){

    //!!!!todo-  Favori Icon tıklanma durumlarını burada tutuyoruz (yemek_id üzerinden)
    val favoriSet = mutableSetOf<Int>()

    inner class CardDesignHolder(var cardBinding: CardDesignBinding) : RecyclerView.ViewHolder(cardBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDesignHolder {
        //viewBinding kurulumu burada
        // todo: data binding işlemi**/
        val binding: CardDesignBinding = DataBindingUtil.inflate<CardDesignBinding>(
            LayoutInflater.from(mContext),
            R.layout.card_design,
            parent,
            false
        )
        return CardDesignHolder(binding)
    }

    override fun onBindViewHolder(holder: CardDesignHolder, position: Int) {
        /** card View ile ilgili tüm işlemler (tıklama vs) burada olacak
         * 1-holder nesnesi sayesinde CardDesignHolder classındaki  cardBinding'e ulaşıcaz
         * 2-position ise bir döngünün indexi gibi düşün (her 1 nesneye teker teker ulaşacak)
         * 3-AnasayfaFragment içinde oluşturulan recyclerView e gönderilen yemekListesi burada doldurulur (karşılanır)
         **/
        val cBinding = holder.cardBinding
        val yemek = yemeklerList.get(position)
        cBinding.yemekObj = yemek  // todo: xml ve fragmenttaki nesneler eşleştirilir


       //todo:resmi almak için (sonra sil )
        val imageId = mContext.resources.getIdentifier(
            yemek.yemek_resim_adi,
            "drawable",
            mContext.packageName
        )
        if (imageId != 0) {
            cBinding.imageViewYemekImg.setImageResource(imageId)
        } else {
            cBinding.imageViewYemekImg.setImageResource(R.drawable.favblank_img)
        }


//        todo: retrofit & glide ile internete yüklenen resmi alma
//        val imgUrl = "http://kasimadalan.pe.hu/filmler_yeni/resimler/${film.resim}"
//        Glide.with(mContext)
//            .load(imgUrl)
//            .override(500,700)
//            .into(cBinding.imageViewFilmImg)
//
       /*todo-  card View tıklama & veri transferi & sayfa geçişi - - geçici verilerle
        * hatırlatma : cardView yapısı AnasayfaFragment içinde, o yüzden **directions** o sayfa **args** UrunDetayFragment
        * hatırlatma : main_activity_nav içinde yemek nesnesi argument olarak ekli olmalı
        */

         cBinding.cardViewYemekler.setOnClickListener {
             val gecis= AnasayfaFragmentDirections.urunDetayGecis(yemek = yemek)
             Navigation.findNavController(it).navigate(gecis)
         }

      //todo- favori iconu güncellemek
        if (favoriSet.contains(yemek.yemek_id)) {
            cBinding.imageViewFav.setImageResource(R.drawable.favfill_img)
        } else {
            cBinding.imageViewFav.setImageResource(R.drawable.favblank_img)
        }

        cBinding.imageViewFav.setOnClickListener {
            if (favoriSet.contains(yemek.yemek_id)) {
                favoriSet.remove(yemek.yemek_id)
            } else {
                favoriSet.add(yemek.yemek_id)
            }
            notifyItemChanged(position)
        }


//        /**Sepet butonuna tıklama işlemi**
//         */
//        cBinding.buttonSepet.setOnClickListener {
//            Snackbar.make(it," \"${film.ad}\" sepete eklendi. ",Snackbar.LENGTH_SHORT).show()
//        }
//
//

}


    override fun getItemCount(): Int {
        return yemeklerList.size
    }
}