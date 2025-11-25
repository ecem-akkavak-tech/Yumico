package com.ecemm.yumico.ui.adapter
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ecemm.yumico.R
import com.ecemm.yumico.data.entity.Yemekler
import com.ecemm.yumico.databinding.CardDesignBinding
import com.ecemm.yumico.ui.fragment.AnasayfaFragmentDirections
import com.ecemm.yumico.ui.viewmodel.FavoriViewModel
import com.ecemm.yumico.ui.viewmodel.auth.AuthViewModel

class YemeklerAdapter(
    var mContext:Context ,
    var yemeklerList:List<Yemekler> ,
    var favoriViewModel: FavoriViewModel,
    var authViewModel : AuthViewModel
) : RecyclerView.Adapter<YemeklerAdapter.CardDesignHolder>() {


    inner class CardDesignHolder(var cardBinding: CardDesignBinding) :
        RecyclerView.ViewHolder(cardBinding.root)

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
            .override(500, 700)
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
        val favoriList = favoriViewModel.favoriListesi.value

        if (favoriList?.any { it.yemek_adi == yemek.yemek_adi } == true) {
            cBinding.imageViewFav.setImageResource(R.drawable.favfill_img)
        } else {
            cBinding.imageViewFav.setImageResource(R.drawable.favblank_img)
        }


        //güncel ratingbar ui:
        val favYemek = favoriList?.find { it.yemek_adi == yemek.yemek_adi }
        cBinding.ratingBarAnasayfa.rating = favYemek?.rating ?: 0f

        cBinding.ratingBarAnasayfa.setOnRatingBarChangeListener { _, rating, _ ->
            if (favYemek != null) {
                favoriViewModel.favoriRatingGuncelle(favYemek.yemek_id, favYemek.user_id, rating)
            }
        }

        cBinding.imageViewFav.setOnClickListener {
            val currentRating = cBinding.ratingBarAnasayfa.rating

            authViewModel.getUserFromFirestore() { currentUser ->
                val uID = currentUser?.userId ?: return@getUserFromFirestore //firestoredaki uid
                val mevcutFavYemek = favoriList?.find { it.yemek_adi == yemek.yemek_adi }

                        if (mevcutFavYemek != null) {
                            // Eğer favoride varsa, gerçek ID ile sil
                            favoriViewModel.favoriSil(mevcutFavYemek.yemek_id , mevcutFavYemek.user_id)
                            cBinding.imageViewFav.setImageResource(R.drawable.favblank_img)
                        } else {
                            favoriViewModel.favoriEkle(
                                uID,
                                yemek.yemek_adi,
                                yemek.yemek_resim_adi,
                                yemek.yemek_fiyat,
                                currentRating
                            )
                            cBinding.imageViewFav.setImageResource(R.drawable.favfill_img)
                        }

                Log.e("CURRENT USER:", "${currentUser?.name.toString()}")
                }
        }


        cBinding.imageViewFav.animate().scaleX(1.2f).scaleY(1.2f).setDuration(150).withEndAction {
            cBinding.imageViewFav.animate().scaleX(1f).scaleY(1f).duration = 150
        }

    }



    override fun getItemCount(): Int {
        return yemeklerList.size
    }
}