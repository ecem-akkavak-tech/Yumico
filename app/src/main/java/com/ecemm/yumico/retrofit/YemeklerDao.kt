package com.ecemm.yumico.retrofit
import com.ecemm.yumico.data.entity.CrudResponse
import com.ecemm.yumico.data.entity.YemekSepetiResponse
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
        @Field("yemek_fiyat") yemek_fiyat:Int,
        @Field("yemek_siparis_adet")  yemek_siparis_adet:Int,
        @Field("kullanici_adi") kullanici_adi:String
    ):CrudResponse


    //TODO- **POST** Sepetteki Tüm yemekleri getir -kullanıcıya göre
    @POST("yemekler/sepettekiYemekleriGetir.php")
    @FormUrlEncoded
    suspend fun sepettekiTumYemekleriGetir(
         @Field("kullanici_adi") kullanici_adi:String
    ):YemekSepetiResponse

    //TODO- **POST** Sepetteki Yemeği sil -kullanıcıya & idsine göre
    @POST("yemekler/sepettenYemekSil.php")
    @FormUrlEncoded
    suspend fun yemekSil(
        @Field("sepet_yemek_id") sepet_yemek_id:Int,
        @Field("kullanici_adi") kullanici_adi:String,
    ):CrudResponse
}