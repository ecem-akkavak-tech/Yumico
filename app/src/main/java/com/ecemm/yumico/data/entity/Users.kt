package com.ecemm.yumico.data.entity
import java.io.Serializable

data class Users(
    var user_id:String? = "",
    var user_name:String? = "",
    var user_surname:String? = "",
    var user_telephone:String? = "",
    var user_address:String? = "",
    var user_profileImgUrl:String? = "",
):Serializable{}


/* FIRESTORE'DA KARŞILIĞI:
 users (collection)
 └── uid_12345 (document)
      ├── name: "Ecem"
      ├── surname: "Görüşük"
      ├── telephone: "0534..."
      ├── address: "İzmir"
      ├── profileImgUrl: "https://firebasestorage..."
*/