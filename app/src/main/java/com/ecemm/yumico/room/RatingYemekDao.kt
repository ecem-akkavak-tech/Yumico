package com.ecemm.yumico.room
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ecemm.yumico.data.entity.FavoriYemek
import com.ecemm.yumico.data.entity.RatingYemek

@Dao
interface  RatingYemekDao {

    //todo: VERİ EKLEME (Create)
    @Insert(onConflict = OnConflictStrategy.REPLACE) //Aynı ID varsa üzerine yazar (yemek_id)
    suspend fun ratingInsert(ratingYemek: RatingYemek)

    //todo: VERİ GETİRME (Read)
    @Query("SELECT * FROM rating_yemekler WHERE yemek_id = :yemek_id")
    suspend fun getRating(yemek_id:Int):RatingYemek?
}