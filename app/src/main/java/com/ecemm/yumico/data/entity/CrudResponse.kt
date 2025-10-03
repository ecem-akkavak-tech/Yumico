package com.ecemm.yumico.data.entity
import java.io.Serializable

data class CrudResponse(
    var success:Int,
    var message:String)
    : Serializable {
}