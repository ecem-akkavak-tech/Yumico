package com.ecemm.yumico.data.datasource
import android.util.Log
import com.ecemm.yumico.data.entity.YemekSepeti
import com.ecemm.yumico.data.entity.Yemekler
import com.ecemm.yumico.retrofit.YemeklerDao
import com.ecemm.yumico.utils.SiralamaTuru
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import retrofit2.http.Field

class YemeklerDataSource(var yemeklerDao : YemeklerDao) {

    //TODO- Tüm yemekleri getir -GET
    suspend fun yemekleriGetir() : List<Yemekler> = withContext(Dispatchers.IO){
        return@withContext yemeklerDao.yemekleriYukle().yemekler
    }

    //TODO- Yemek ara -POST
    suspend fun yemekAra(aramaKelimesi:String,tumYemekler:List<Yemekler>,siralamaTuru: SiralamaTuru):List<Yemekler> = withContext(Dispatchers.Default) {
        //no api call for this process
        val filtrelenen = tumYemekler.filter {
            it.yemek_adi.contains(aramaKelimesi, ignoreCase = true)
        }
        return@withContext when(siralamaTuru){
            //fiyata göre artan & azalan sıralama işlemi
            SiralamaTuru.ARTAN  -> filtrelenen.sortedBy { it.yemek_fiyat.toInt() } //sortedBy: artan sıralamayı verir
            SiralamaTuru.AZALAN -> filtrelenen.sortedByDescending { it.yemek_fiyat.toInt() } //sortedByDescending:azalan sıralama
            else -> filtrelenen //olduğu gibi filtreler
        }
    }


    //TODO- Sepete yemek ekle -POST
    suspend fun sepeteYemekEkle(yemekAdi:String,yemekResimAdi:String,yemekFiyat:Int,yemekSiparisAdet:Int,kullaniciAdi:String){
        yemeklerDao.sepeteYemekEkle(yemekAdi,yemekResimAdi,yemekFiyat,yemekSiparisAdet,kullaniciAdi)
        Log.e("eklendi: ","Yemek Adı: ${yemekAdi} Fiyat: ${yemekFiyat} Kullanıcı: ${kullaniciAdi}")
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