package com.ecemm.yumico.retrofit
import com.ecemm.yumico.data.entity.CrudResponse
import com.ecemm.yumico.data.entity.YemeklerResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

//DAO interfaceinin amacı web servislerini çalışmasını sağlamaktır (bu servisleri dataSource'ta kullanıyoruz)
interface YemeklerDao {

    //TODO- **GET** Tüm yemekleri getir
    @GET("yemekler/tumYemekleriGetir.php")
    suspend fun yemekleriYukle():YemeklerResponse
    //bu yemekleriYukle() metodu içi doldurulduğunda YemeklerResponse'nun bir nesnesi gibi davranacak


    //TODO- **POST** Sepete yemek ekle
    @POST("yemekler/sepeteYemekEkle.php")
    @FormUrlEncoded //Türkçe desteği
    suspend fun sepeteYemekEkle(
        @Field("yemek_adi") yemek_adi:String,
        @Field("yemek_resim_adi") yemek_resim_adi:String,
        @Field("yemek_fiyat") yemek_fiyat:Int
    ):CrudResponse

}