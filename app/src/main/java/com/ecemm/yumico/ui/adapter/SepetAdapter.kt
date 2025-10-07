package com.ecemm.yumico.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ecemm.yumico.R
import com.ecemm.yumico.data.entity.YemekSepeti
import com.ecemm.yumico.databinding.SepetCardDesignBinding
import com.ecemm.yumico.ui.viewmodel.SepetViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SepetAdapter(
    var mContext: Context,
    var sepettekiYemeklerListesi:List<YemekSepeti>,
    var viewModel: SepetViewModel
) : RecyclerView.Adapter<SepetAdapter.CardDesignHolder>(){

    inner class CardDesignHolder(var cardBinding : SepetCardDesignBinding) : RecyclerView.ViewHolder(cardBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDesignHolder {
        //viewBinding kurulumu burada yapılır
        // todo: data binding işlemi**/
        val binding:SepetCardDesignBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            R.layout.sepet_card_design,parent,
            false
        )
        return CardDesignHolder(binding)
    }


    override fun onBindViewHolder(holder: CardDesignHolder, position: Int) {

        val cBinding = holder.cardBinding
        val sepeteEklenenYemek = sepettekiYemeklerListesi.get(position)
        cBinding.yemekSepetiObj = sepeteEklenenYemek  // todo: xml ve fragmenttaki nesneler eşleştirilir


        /*TODO- retrofit & glide ile internete yüklenen resmi alma  */
        val imgUrl = "http://kasimadalan.pe.hu/yemekler/resimler/${sepeteEklenenYemek.yemek_resim_adi}"
        Glide.with(mContext)
            .load(imgUrl)
            .override(500,700)
            .into(cBinding.imageViewSepetYemekImg)


        cBinding.imageViewSil.setOnClickListener {
            Snackbar.make(it,"${sepeteEklenenYemek.yemek_adi} silinsin mi? ",Snackbar.LENGTH_SHORT)
                .setAction("EVET"){
                    yemekSil(sepeteEklenenYemek.sepet_yemek_id,sepeteEklenenYemek.kullanici_adi,sepeteEklenenYemek.yemek_adi)

                }.show()
            Log.e("sepettekiYemeklerListesi",sepettekiYemeklerListesi.toString())
        }
    }

    //TODO- Sepetteki yemeği sil
    fun yemekSil(sepet_yemek_id:Int,kullanici_adi:String,yemek_adi:String){
           viewModel.yemekSil(sepet_yemek_id, kullanici_adi,yemek_adi)
    }

    override fun getItemCount(): Int {
        return sepettekiYemeklerListesi.size
    }
}