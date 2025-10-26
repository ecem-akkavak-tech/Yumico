package com.ecemm.yumico.data.entity
import java.io.Serializable

data class Users(
    var userId:String? = "",
    var name:String? = "",
    var surname:String? = "",
    var telephone:String? = "",
    var address:String? = "",
    var profileImgUrl:String? = "", //base64
):Serializable{}


/* FIRESTORE'DA KARŞILIĞI:
 users (collection)
 └── uid_12345 (document)
      ├── name: "Ecem"
      ├── surname: "Görüşük"
      ├── telephone: "0534..."
      ├── address: "İzmir"
      ├── profileImgUrl: ".."
*/