package com.ecemm.yumico.ui.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ecemm.yumico.R
import com.ecemm.yumico.data.entity.Yemekler
import com.ecemm.yumico.databinding.CardDesignBinding
import kotlinx.coroutines.flow.internal.NoOpContinuation.context

class YemeklerAdapter(var mContext:Context , var yemeklerList:List<Yemekler>
) : RecyclerView.Adapter<YemeklerAdapter.CardDesignHolder>(){

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

//yemek_resim_ad
       //todo:resmi almak için (sonra sil )
        val imageId = context.resources.getIdentifier(yemek.yemek_resim_ad, "drawable", context.packageName)
        cBinding.imageViewYemekImg.setImageResource(imageId)

//        todo: retrofit & glide ile internete yüklenen resmi alma
//        val imgUrl = "http://kasimadalan.pe.hu/filmler_yeni/resimler/${film.resim}"
//        Glide.with(mContext)
//            .load(imgUrl)
//            .override(500,700)
//            .into(cBinding.imageViewFilmImg)
//
//        /** card View tıklama & veri transferi & sayfa geçişi
//         * 4-cardView'a (her bir card'a ) tıklanınca detailFragment sayfasına geçiş yapılsın ve her bir filmin detayı görüntülensin
//         * hatırlatma : cardView yapısı HomePageFragment içinde, o yüzden **directions** o sayfa **args** DetailFragment
//         * hatırlatma : main_activity_nav içinde film nesnesi argument olarak ekli olmalı
//         **/
//        cBinding.cardViewFilm.setOnClickListener{
//            val gecis = HomepageFragmentDirections.detailPageGecis(film=film)
//            Navigation.findNavController(it).navigate(gecis)
//        }
//
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