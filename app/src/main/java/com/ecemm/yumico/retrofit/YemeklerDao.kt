package com.ecemm.yumico.retrofit
import com.ecemm.yumico.data.entity.YemeklerResponse
import retrofit2.http.GET

//DAO interfaceinin amacı web servislerini çalışmasını sağlamaktır (bu servisleri dataSource'ta kullanıyoruz)
interface YemeklerDao {

    //TODO- **GET** Tüm yemekleri getir
    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun yemekleriYukle():YemeklerResponse
    //bu yemekleriYukle() metodu içi doldurulduğunda YemeklerResponse'nun bir nesnesi gibi davranacak



}