package com.ecemm.yumico.room
import androidx.room.Database
import androidx.room.RoomDatabase
import com.ecemm.yumico.data.entity.FavoriYemek
import com.ecemm.yumico.data.entity.RatingYemek

//entities içindeki ilgili tablo yazılır, 1 tablo ; 1 interface
//her yeni table ekleme sonrası versiyonu 1 arttır
@Database(
    entities = [FavoriYemek::class, RatingYemek::class],
    version = 2)

abstract class Database : RoomDatabase(){
    abstract fun getFavoriDao() : FavoriDao
    abstract fun getRatingYemekDao() : RatingYemekDao
}