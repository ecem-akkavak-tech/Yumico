package com.ecemm.yumico.ui.viewmodel
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

    init{
        /* Todo: Uygulamanın ilk açıldığı anda veri getirmesi için init gerekir  */

    }

    //TODO- Sepetteki Tüm yemekleri  getir
     fun sepettekiYemekleriGetir(kullanici_adi:String){
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response=yemeklerRepository.sepettekiYemekleriGetir(kullanici_adi)
                sepetListesi.value = response ?: emptyList()
            }catch (e:Exception){
                e.printStackTrace()
                sepetListesi.value = emptyList()
            }
        }
    }
}