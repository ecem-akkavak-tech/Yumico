package com.ecemm.yumico.data.datasource
import android.util.Log
import com.ecemm.yumico.data.entity.Yemekler
import com.ecemm.yumico.retrofit.YemeklerDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class YemeklerDataSource(var yemeklerDao : YemeklerDao) {

    //TODO- Tüm yemekleri getir
    suspend fun yemekleriGetir() : List<Yemekler> = withContext(Dispatchers.IO){
        return@withContext yemeklerDao.yemekleriYukle().yemekler
    }

    //TODO- Yemek ara
    suspend fun yemekAra(aramaKelimesi:String,tumYemekler:List<Yemekler>):List<Yemekler> = withContext(Dispatchers.Default){
      //no api call for this process
      tumYemekler.filter {
          it.yemek_adi.contains(aramaKelimesi,ignoreCase = true)
      }
    }

    //TODO- Sepete yemek ekle
    suspend fun sepeteYemekEkle(yemekAdi:String,yemekResimAdi:String,yemekFiyat:Int,yemekSiparisAdet:Int,kullaniciAdi:String){
        yemeklerDao.sepeteYemekEkle(yemekAdi,yemekResimAdi,yemekFiyat,yemekSiparisAdet,kullaniciAdi)
        Log.e("eklendi: ","Yemek Adı: ${yemekAdi} Fiyat: ${yemekFiyat} ")
    }
}