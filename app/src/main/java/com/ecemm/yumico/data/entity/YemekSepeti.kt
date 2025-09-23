package com.ecemm.yumico.data.entity

import java.io.Serializable

data class YemekSepeti(
    var yemekler:List<Yemekler>,
    var yemekAdet:Int,

):Serializable {
}