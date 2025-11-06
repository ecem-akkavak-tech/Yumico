package com.ecemm.yumico.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ecemm.yumico.data.repo.RatingYemekRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RatingYemekViewModel  @Inject constructor(var ratingYemekRepository: RatingYemekRepository) : ViewModel(){

    //TODO- RatingBar
    private val _ratingBarValue = MutableLiveData<Float>(0f)
    val ratingBarValue: LiveData<Float> get() = _ratingBarValue  //not: LiveData'yı bir interface gibi düşün → direkt instance'ı alınmaz.

    fun ratingEkle(yemek_id:Int,yemek_adi:String,rating:Float){
        CoroutineScope(Dispatchers.Main).launch {
            ratingYemekRepository.ratingEkle(yemek_id,yemek_adi,rating)
            _ratingBarValue.value = rating // anlık UI güncellemesi için
        }
    }

    fun ratingGetir(yemek_id:Int){
        CoroutineScope(Dispatchers.Main).launch {
            val ratingYemek= ratingYemekRepository.ratingGetir(yemek_id)
            _ratingBarValue.value = ratingYemek?.rating   ?:  0f //başta rating varsa onu , yoksa 0 float kullan
        }
    }


}