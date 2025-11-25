package com.ecemm.yumico.room
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ecemm.yumico.data.entity.FavoriYemek

//entities içindeki ilgili tablo yazılır, 1 tablo ; 1 interface
//her yeni table & column ekleme sonrası versiyonu 1 arttır
@Database(
    entities = [FavoriYemek::class],
    version = 5)

abstract class Database : RoomDatabase() {
    abstract fun getFavoriDao(): FavoriDao
}

val MIGRATION_4_5 = object : Migration(4,5) {
    override fun migrate(database: SupportSQLiteDatabase) {
        database.execSQL(
            "ALTER TABLE favori_yemekler ADD COLUMN user_id TEXT NOT NULL DEFAULT ''"
        )
    }
}
