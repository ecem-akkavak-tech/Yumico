package com.ecemm.yumico.ui.adapter
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ecemm.yumico.R
import com.ecemm.yumico.data.entity.Yemekler

class YemeklerAdapter(var mContext:Context , var yemeklerList:List<Yemekler>
) : RecyclerView.Adapter<YemeklerAdapter.CardDesignHolder>(){

    inner class CardDesignHolder(var cardBinding: ViewDataBinding) : RecyclerView.ViewHolder(cardBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardDesignHolder {
        //viewBinding kurulumu burada
        // todo: data binding işlemi**/
        val binding: ViewDataBinding = DataBindingUtil.inflate(
            LayoutInflater.from(mContext),
            R.layout.card_tasarim,
            parent,
           false
        )
        return CardDesignHolder(binding)
    }



    override fun onBindViewHolder(holder: CardDesignHolder, position: Int) {

    }


    override fun getItemCount(): Int {
        return yemeklerList.size
    }
}