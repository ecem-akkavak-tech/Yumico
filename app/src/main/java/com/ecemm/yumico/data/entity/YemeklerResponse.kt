package com.ecemm.yumico.data.entity

import java.io.Serializable

data class YemeklerResponse(
    var yemekler:List<Yemekler>,
    var success: Int
):Serializable {
}