package com.ecemm.yumico.data.repo

import com.ecemm.yumico.data.datasource.FavoriDataSource
import com.ecemm.yumico.data.entity.FavoriYemek

class FavoriRepository(var favoriDataSource: FavoriDataSource){

    suspend fun favoriEkle(yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:Int,rating:Float){
        return favoriDataSource.favoriEkle(yemek_adi, yemek_resim_adi, yemek_fiyat,rating)
    }

    suspend fun favoriSil(yemek_id:Int){
        return favoriDataSource.favoriSil(yemek_id)
    }

    suspend fun favoriYemekleriGetir():List<FavoriYemek>{
        return  favoriDataSource.favoriYemekleriGetir()
    }

    suspend fun favoriRatingGuncelle(yemekId: Int, rating: Float){
        return favoriDataSource.favoriRatingGuncelle(yemekId, rating)
    }
}