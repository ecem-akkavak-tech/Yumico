package com.ecemm.yumico.room
import androidx.room.Database
import androidx.room.RoomDatabase
import com.ecemm.yumico.data.entity.FavoriYemek

//entities içindeki ilgili tablo yazılır, 1 tablo ; 1 interface
//her yeni table ekleme sonrası versiyonu 1 arttır
@Database(
    entities = [FavoriYemek::class],
    version = 4)

abstract class Database : RoomDatabase() {
    abstract fun getFavoriDao(): FavoriDao
}
