package com.ecemm.yumico.room
import androidx.room.Database
import androidx.room.RoomDatabase
import com.ecemm.yumico.data.entity.FavoriYemek

@Database(entities = [FavoriYemek::class], version = 1) //entities içindeki ilgili tablo yazılır, 1 tablo ; 1 interface
abstract class Database : RoomDatabase(){
    abstract fun getFavoriDao() : FavoriDao
}