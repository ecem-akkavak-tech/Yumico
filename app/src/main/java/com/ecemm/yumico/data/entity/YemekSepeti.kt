package com.ecemm.yumico.data.entity
import java.io.Serializable

data class YemekSepeti(
     var yemek_adi:String,
     var yemek_resim_adi:String,
     var yemek_fiyat:Int,
     var yemek_siparis_adet:Int,
     var kullanici_adi:String

):Serializable {}
