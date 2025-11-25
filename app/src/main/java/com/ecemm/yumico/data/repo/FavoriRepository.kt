package com.ecemm.yumico.data.repo

import com.ecemm.yumico.data.datasource.FavoriDataSource
import com.ecemm.yumico.data.entity.FavoriYemek

class FavoriRepository(var favoriDataSource: FavoriDataSource){

    suspend fun favoriEkle(user_id:String,yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:Int,rating:Float){
        return favoriDataSource.favoriEkle(user_id, yemek_adi, yemek_resim_adi, yemek_fiyat, rating)
    }

    suspend fun favoriSil(yemek_id:Int , user_id: String){
        return favoriDataSource.favoriSil(yemek_id , user_id)
    }

    suspend fun favoriYemekleriGetir(user_id:String):List<FavoriYemek>{
        return  favoriDataSource.favoriYemekleriGetir(user_id)
    }

    suspend fun favoriRatingGuncelle(yemekId: Int, user_id: String ,rating: Float){
        return favoriDataSource.favoriRatingGuncelle(yemekId, user_id, rating)
    }
}

