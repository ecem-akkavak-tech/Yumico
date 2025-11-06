package com.ecemm.yumico.data.entity
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "rating_yemekler")
data class RatingYemek(
    @PrimaryKey var yemek_id: Int,  // Retrofit'ten gelen yemek_id
    @ColumnInfo(name = "yemek_adi") var yemek_adi: String,
    @ColumnInfo(name = "rating") var rating: Float
) : Serializable
