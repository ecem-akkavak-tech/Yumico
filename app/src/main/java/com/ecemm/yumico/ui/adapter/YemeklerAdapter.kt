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
    var mContext:Context ,
    var yemeklerList:List<Yemekler> ,
    var viewModel: AnasayfaViewModel
) : RecyclerView.Adapter<YemeklerAdapter.CardDesignHolder>(){

    val favoriSet = mutableSetOf<Int>() //todo-  Favori Iconuna tıklanma durumlarını favoriSet tutuyo (yemek_id üzerinden)

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




       /*TODO- retrofit & glide ile internete yüklenen resmi alma  */
        val imgUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${yemek.yemek_resim_adi}"
        Glide.with(mContext)
            .load(imgUrl)
            .override(500,700)
            .into(cBinding.imageViewYemekImg)

       /*todo-  card View tıklama & veri transferi & sayfa geçişi
        * hatırlatma : cardView yapısı AnasayfaFragment içinde, o yüzden **directions** o sayfa **args** UrunDetayFragment
        * hatırlatma : main_activity_nav içinde yemek nesnesi argument olarak ekli olmalı
        */

       cBinding.cardViewYemekler.setOnClickListener { view ->
               val gecis = AnasayfaFragmentDirections.urunDetayGecis(yemek)
               Navigation.findNavController(view).navigate(gecis)
       }


      /*todo- favori iconu güncellemek */
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
            notifyItemChanged(position) //anlık değişimi gösterir
        }
}



    override fun getItemCount(): Int {
        return yemeklerList.size
    }
}