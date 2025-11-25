package com.ecemm.yumico.data.entity
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "favori_yemekler")
data class FavoriYemek(
    @ColumnInfo(name = "yemek_id")  @PrimaryKey(autoGenerate = true) @NotNull var yemek_id:Int,
    @ColumnInfo(name = "user_id")  @NotNull val user_id: String,
    @ColumnInfo(name = "yemek_adi")  @NotNull var yemek_adi:String,
    @ColumnInfo(name = "yemek_resim_adi") @NotNull var yemek_resim_adi:String,
    @ColumnInfo(name = "yemek_fiyat")  @NotNull var yemek_fiyat:Int,
    @ColumnInfo(name = "rating")  @NotNull  var rating: Float
):Serializable{}