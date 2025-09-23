package com.ecemm.yumico.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ecemm.yumico.R
import com.ecemm.yumico.data.entity.YemekSepeti
import com.ecemm.yumico.databinding.SepetCardDesignBinding

class SepetAdapter(var mContext: Context, var yemeklerListesi:List<YemekSepeti>) : RecyclerView.Adapter<SepetAdapter.CardDesignHolder>(){
    inner class CardDesignHolder(var cardBinding : SepetCardDesignBinding) : RecyclerView.ViewHolder(cardBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDesignHolder {
        //viewBinding kurulumu burada yapılır
        // todo: data binding işlemi**/
        val binding:SepetCardDesignBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            R.layout.card_design,parent,
            false
        )
        return CardDesignHolder(binding)
    }


    override fun onBindViewHolder(holder: CardDesignHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return yemeklerListesi.size
    }
}