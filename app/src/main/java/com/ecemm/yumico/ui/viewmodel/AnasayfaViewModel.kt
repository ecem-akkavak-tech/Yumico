package com.ecemm.yumico.ui.viewmodel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ecemm.yumico.data.entity.Yemekler
import com.ecemm.yumico.data.repo.YemeklerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/** Todo: Repository ile bağlama işlemi
  * Todo: Sayfaya veri aktarma işlemi olduğu için Livedata (içinde Yemekler türünde liste içermeli) kullanılmalı
 **/

@HiltViewModel
class AnasayfaViewModel @Inject constructor(var yemeklerRepository: YemeklerRepository) : ViewModel(){

    val yemeklerListesi = MutableLiveData<List<Yemekler>>()  // ilgili liste viewmodel içinde tanımlanır
    init{
       /* Todo: Uygulamanın ilk açıldığı anda veri getirmesi için init gerekir  */
       yemekleriGetir()
    }

    //TODO- Tüm yemekleri getir
    fun yemekleriGetir(){
        CoroutineScope(Dispatchers.Main).launch {
            //eğer yemekleri getirirken veri yoksa,appin çökmemesi için try-catch kullan
            try {
                yemeklerListesi.value = yemeklerRepository.yemekleriGetir()
            }catch (e:Exception){}
        }
    }

    //TODO- Yemek ara
    fun yemekFiltresiyleAra(aramaKelimesi:String){
        val tumListe = yemeklerListesi.value ?: listOf()
        CoroutineScope(Dispatchers.Main).launch {
            yemeklerListesi.value = yemeklerRepository.yemekAra(aramaKelimesi,tumListe)
           
        }
    }
}


/** HATIRLATMA:
 * Her activity veya fragment 1 View Model'e sahiptir.
 * View Modeller fragment & activityleri yönetir
 * View'lardaki fonksiyonlar genelde return'lü çalışmaz.
 * Değer okuma ve üzerindeki fonksiyonları çalıştırma işlemleri burada yapılır (anlık liste değişimleri live data ile).
 ** İlgili activity'e (ya da fragment) veri göndermeliyiz, bunu da activity (ya da fragment) içinde bağlayarak yapabiliriz.
 **/