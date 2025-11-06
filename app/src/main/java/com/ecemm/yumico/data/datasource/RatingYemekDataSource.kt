package com.ecemm.yumico.data.datasource
import android.util.Log
import com.ecemm.yumico.data.entity.RatingYemek
import com.ecemm.yumico.room.RatingYemekDao
//yemek_id,yemek_adi,rating
class RatingYemekDataSource(var ratingYemekDao: RatingYemekDao) {

    suspend fun ratingEkle(yemek_id:Int,yemek_adi:String,rating:Float){
        val ratedYemek= RatingYemek(yemek_id,yemek_adi, rating)
        return ratingYemekDao.ratingInsert(ratedYemek)
        Log.e("rated edilen yemek:",yemek_adi)
    }

    suspend fun ratingGetir(yemek_id:Int) :RatingYemek?{
        return ratingYemekDao.getRating(yemek_id)
    }
}