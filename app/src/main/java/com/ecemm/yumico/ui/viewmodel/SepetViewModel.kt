package com.ecemm.yumico.ui.viewmodel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ecemm.yumico.data.entity.YemekSepeti
import com.ecemm.yumico.data.repo.YemeklerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
/** Todo: Repository ile bağlama işlemi
 * Todo: Sayfaya veri aktarma işlemi olduğu için MutableLivedata (içinde YemekSepeti türünde liste içermeli) kullanılmalı
 **/
@HiltViewModel
class SepetViewModel@Inject constructor(var yemeklerRepository: YemeklerRepository) : ViewModel() {

    val sepetListesi = MutableLiveData<List<YemekSepeti>>()

    //TODO- Sepete yemek ekle - post
    fun sepeteYemekEkle(yemekAdi:String,yemekResimAdi:String,yemekFiyat:Int,yemekSiparisAdet:Int,kullaniciAdi:String){
        CoroutineScope(Dispatchers.Main).launch {
            //main arayüzle ilgili işlemlerde kullanılır
            yemeklerRepository.sepeteYemekEkle(yemekAdi,yemekResimAdi,yemekFiyat,yemekSiparisAdet,kullaniciAdi)

        }
    }



    //TODO- Sepetteki Tüm yemekleri  getir
    fun sepettekiYemekleriGetir(kullanici_adi:String){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                //yemeklerRepository.sepettekiYemekleriGetir(kullanici_adi)
                //sepetListesi.value = response
            }catch (e:Exception){
                e.printStackTrace()
                sepetListesi.value = emptyList()
            }
        }
    }

    //TODO- Sepetteki yemeği sil -*filtre yemek_adina göre*
    fun yemekSil(sepet_yemek_id:Int, kullanici_adi:String, yemek_adi:String){
        CoroutineScope(Dispatchers.Main).launch {
            val mevcutListe = sepetListesi.value?.toMutableList() ?: mutableListOf()
//            android.util.Log.e("SepetVM", "Silinecek id: $sepet_yemek_id")
//            mevcutListe.forEach {
//                android.util.Log.e("SepetVM", "Listedeki id: ${it.sepet_yemek_id} - ${it.yemek_adi}")
//            }
            val yeniListe = mevcutListe.filter { it.yemek_adi != yemek_adi}
            sepetListesi.value = yeniListe //live data update edilir
 //           android.util.Log.e("SepetVM", "Yeni liste: ${yeniListe.map { it.yemek_adi }}")
            try {
                yemeklerRepository.yemekSil(sepet_yemek_id, kullanici_adi)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun toplamSepetHesapla():Int{
            val mevcutListe = sepetListesi.value?.toMutableList() ?: mutableListOf()
            var toplam = 0
            mevcutListe.forEach {
                toplam = toplam + (it.yemek_fiyat * it.yemek_siparis_adet)
            }
            return toplam
        }


}