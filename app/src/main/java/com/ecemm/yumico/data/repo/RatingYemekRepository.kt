package com.ecemm.yumico.data.repo
import com.ecemm.yumico.data.datasource.RatingYemekDataSource
import com.ecemm.yumico.data.entity.RatingYemek

class RatingYemekRepository(var ratingYemekDataSource: RatingYemekDataSource) {

    suspend fun ratingEkle(yemek_id:Int,yemek_adi:String,rating:Float) {
         ratingYemekDataSource.ratingEkle(yemek_id,yemek_adi, rating)
    }

    suspend fun ratingGetir(yemek_id:Int): RatingYemek?{
        return ratingYemekDataSource.ratingGetir(yemek_id)
    }

}