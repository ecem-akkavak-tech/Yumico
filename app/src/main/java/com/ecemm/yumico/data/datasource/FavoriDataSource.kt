package com.ecemm.yumico.data.datasource

import android.util.Log
import com.ecemm.yumico.data.entity.FavoriYemek
import com.ecemm.yumico.room.FavoriDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriDataSource(var favoriDao: FavoriDao) {

    //create
    suspend fun favoriEkle(user_id:String,yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:Int,rating:Float){
        val favorilenenYemek = FavoriYemek(0,user_id,yemek_adi,yemek_resim_adi,yemek_fiyat,rating)
        favoriDao.favoriEkle(favorilenenYemek)
        Log.e("favorilere eklenen yemek:",yemek_resim_adi)
    }

    //read
    suspend fun favoriYemekleriGetir(userId:String) : List<FavoriYemek> = withContext(Dispatchers.IO){
        return@withContext favoriDao.favoriYemekleriGetir(userId)
    }

    //update rating column
    suspend fun favoriRatingGuncelle(yemekId: Int,user_id:String , rating: Float){
        favoriDao.favoriRatingGuncelle(yemekId, user_id, rating)
    }

    //delete
    suspend fun favoriSil(yemekId: Int , userId: String) {
        favoriDao.favoriSilById(yemekId , userId)
    }
}
