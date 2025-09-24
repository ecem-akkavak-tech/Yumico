package com.ecemm.yumico.data.entity
import java.io.Serializable

data class YemekSepeti(
    var yemek:Yemekler,
    var yemekAdet:Int,

):Serializable {
}