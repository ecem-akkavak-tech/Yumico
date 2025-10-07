package com.ecemm.yumico.data.datasource
import android.util.Log
import com.ecemm.yumico.data.entity.YemekSepeti
import com.ecemm.yumico.data.entity.Yemekler
import com.ecemm.yumico.retrofit.YemeklerDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.http.Field

class YemeklerDataSource(var yemeklerDao : YemeklerDao) {

    //TODO- Tüm yemekleri getir -GET
    suspend fun yemekleriGetir() : List<Yemekler> = withContext(Dispatchers.IO){
        return@withContext yemeklerDao.yemekleriYukle().yemekler
    }

    //TODO- Yemek ara -POST
    suspend fun yemekAra(aramaKelimesi:String,tumYemekler:List<Yemekler>):List<Yemekler> = withContext(Dispatchers.Default){
      //no api call for this process
      tumYemekler.filter {
          it.yemek_adi.contains(aramaKelimesi,ignoreCase = true)
      }
    }

    //TODO- Sepete yemek ekle -POST
    suspend fun sepeteYemekEkle(yemekAdi:String,yemekResimAdi:String,yemekFiyat:Int,yemekSiparisAdet:Int,kullaniciAdi:String){
        yemeklerDao.sepeteYemekEkle(yemekAdi,yemekResimAdi,yemekFiyat,yemekSiparisAdet,kullaniciAdi)
        Log.e("eklendi: ","Yemek Adı: ${yemekAdi} Fiyat: ${yemekFiyat} ")
    }

    //TODO- Sepetteki Tüm yemekleri  kullanıcıya göre getir -POST
    suspend fun sepettekiYemekleriGetir(kullanici_adi:String) : List<YemekSepeti> = withContext(Dispatchers.IO){
         return@withContext yemeklerDao.sepettekiTumYemekleriGetir(kullanici_adi).yemekSepeti
    }

    //TODO- Sepetteki Yemeği sil -kullanıcıya & idsine göre -POST
    suspend fun yemekSil(sepet_yemek_id:Int,kullanici_adi:String){
         yemeklerDao.yemekSil(sepet_yemek_id,kullanici_adi)
    }
}