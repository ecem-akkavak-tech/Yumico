package com.ecemm.yumico.data.datasource

import android.util.Log
import com.ecemm.yumico.data.entity.FavoriYemek
import com.ecemm.yumico.room.FavoriDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriDataSource(var favoriDao: FavoriDao) {

    //create
    suspend fun favoriEkle(yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:Int){
        val favorilenenYemek = FavoriYemek(0,yemek_adi,yemek_resim_adi,yemek_fiyat)
        favoriDao.favoriEkle(favorilenenYemek)
        Log.e("favorilere eklenen yemek:",yemek_adi.toString())
    }

    //delete
    suspend fun favoriSil(yemek_id:Int){
        val silinecekFavoriYemek = FavoriYemek(yemek_id,"","",0)
        favoriDao.favoriSil(silinecekFavoriYemek)
        Log.e("silinen favori yemek:",yemek_id.toString())
    }

    //read
    suspend fun favoriYemekleriGetir() : List<FavoriYemek> = withContext(Dispatchers.IO){
        return@withContext favoriDao.favoriYemekleriGetir()
    }
}