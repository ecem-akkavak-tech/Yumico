package com.ecemm.yumico.room
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ecemm.yumico.data.entity.FavoriYemek

@Dao
interface FavoriDao {

    //todo: VERİ EKLEME (Create)
    @Insert(onConflict = OnConflictStrategy.REPLACE) //Aynı ID varsa üzerine yazar
    suspend fun favoriEkle(favoriYemek :FavoriYemek)

    //todo: VERİ SİLME (Delete)
    // ID ile silme
    @Query("DELETE FROM favori_yemekler WHERE yemek_id = :yemekId")
    suspend fun favoriSilById(yemekId: Int)


    //todo: VERİ GETİRME (Read)
    @Query("SELECT * FROM favori_yemekler")
    suspend fun favoriYemekleriGetir() : List<FavoriYemek>
}