package com.ecemm.yumico.retrofit

//bu class sayesinde base urlmizi tanımlıyoruz & YemeklerDao'ya erişim sağlıyoruz
class ApiUtils {
    companion object{
        val BASE_URL="http://kasimadalan.pe.hu/"

        //todo* YemeklerDao interfaceine ait fonksiyonumuz (her interface için ayrı bir fonksiyon gereklidir)
        fun getYemeklerDao() :YemeklerDao {
            return RetrofitClient.getClient(BASE_URL).create(YemeklerDao::class.java)
        }

        //getSepetDao gerekeiblir
    }
}