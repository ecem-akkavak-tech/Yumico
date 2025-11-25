package com.ecemm.yumico.room
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ecemm.yumico.data.entity.FavoriYemek

@Dao
interface FavoriDao {

    //todo: VERİ EKLEME (Create) -Kullanıcıya göre
    @Insert(onConflict = OnConflictStrategy.REPLACE) //Aynı ID varsa üzerine yazar (yemek_id & user_id)
    suspend fun favoriEkle(favoriYemek :FavoriYemek)


    //todo: VERİ GETİRME (Read) -Kullanıcıya göre
    @Query("SELECT * FROM favori_yemekler  WHERE user_id = :userId")
    suspend fun favoriYemekleriGetir(userId:String) : List<FavoriYemek>


    @Query("UPDATE favori_yemekler SET rating = :rating WHERE yemek_id = :yemekId AND user_id = :userId")
    suspend fun favoriRatingGuncelle(yemekId: Int, userId: String, rating: Float)



    //todo: VERİ SİLME (Delete)  -Kullanıcıya göre
    // ID ile silme
    @Query("DELETE FROM favori_yemekler WHERE yemek_id = :yemekId AND user_id = :userId")
    suspend fun favoriSilById(yemekId: Int, userId: String)

}