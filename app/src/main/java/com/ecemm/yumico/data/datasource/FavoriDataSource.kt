package com.ecemm.yumico.data.datasource

import android.util.Log
import com.ecemm.yumico.data.entity.FavoriYemek
import com.ecemm.yumico.room.FavoriDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriDataSource(var favoriDao: FavoriDao) {

    //create
    suspend fun favoriEkle(yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:Int,rating:Float){
        val favorilenenYemek = FavoriYemek(0,yemek_adi,yemek_resim_adi,yemek_fiyat,rating)
        favoriDao.favoriEkle(favorilenenYemek)
        Log.e("favorilere eklenen yemek:",yemek_resim_adi.toString())
    }

    //delete
    suspend fun favoriSil(yemekId: Int) {
        favoriDao.favoriSilById(yemekId)
        Log.e("silinen favori yemek:", yemekId.toString())
    }

    //read
    suspend fun favoriYemekleriGetir() : List<FavoriYemek> = withContext(Dispatchers.IO){
        return@withContext favoriDao.favoriYemekleriGetir()
    }

    //update rating column
    suspend fun favoriRatingGuncelle(yemekId: Int, rating: Float){
        favoriDao.favoriRatingGuncelle(yemekId, rating)
    }
}
