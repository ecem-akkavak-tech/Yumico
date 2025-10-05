package com.ecemm.yumico.ui.viewmodel
import androidx.lifecycle.ViewModel
import com.ecemm.yumico.data.repo.YemeklerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UrunDetayViewModel@Inject constructor(var yemeklerRepository: YemeklerRepository) : ViewModel() {
    //TODO- Sepete yemek ekle - post
    fun sepeteYemekEkle(yemekAdi:String,yemekResimAdi:String,yemekFiyat:Int,yemekSiparisAdet:Int,kullaniciAdi:String){
        CoroutineScope(Dispatchers.Main).launch {
            //main arayüzle ilgili işlemlerde kullanılır
             yemeklerRepository.sepeteYemekEkle(yemekAdi,yemekResimAdi,yemekFiyat,yemekSiparisAdet,kullaniciAdi)
        }
    }
}