package com.ecemm.yumico.ui.viewmodel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecemm.yumico.data.entity.FavoriYemek
import com.ecemm.yumico.data.repo.FavoriRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
@HiltViewModel
class FavoriViewModel  @Inject constructor (var favoriRepository: FavoriRepository): ViewModel(){
    val favoriListesi = MutableLiveData<List<FavoriYemek>>()

    init{
        // todo: Uygulamanın ilk açıldığı anda veri getirmesi için init gerekir
        //ViewModel ilk çalıştığı anda kişilerListesini tetikle ve getir
        favoriYemekleriGetir()
    }

    fun favoriYemekleriGetir(){
        CoroutineScope(Dispatchers.Main).launch {
            favoriListesi.value = favoriRepository.favoriYemekleriGetir()
        }
    }

    fun favoriEkle(yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:Int){
        //fe & be kısımları güncel olmalı
        CoroutineScope(Dispatchers.Main).launch {
            //aynı yemeği tekrar tekrar favorilere eklememek için:
            val mevcutFavoriler = favoriRepository.favoriYemekleriGetir()
            if (mevcutFavoriler.any { it.yemek_adi == yemek_adi }) {
                // zaten var, ekleme yapma
                return@launch
            }
            favoriRepository.favoriEkle(yemek_adi, yemek_resim_adi, yemek_fiyat)

            val guncelListe = favoriRepository.favoriYemekleriGetir()
            withContext(Dispatchers.Main){
                favoriListesi.value = guncelListe //ekleme işleminden sonra ilgili sayfa güncellensin
            }
        }
    }

    fun favoriSil(yemek_id: Int){
        //fe & be kısımları güncel olmalı
        viewModelScope.launch(Dispatchers.IO){
            favoriRepository.favoriSil(yemek_id)
            val guncelListe = favoriRepository.favoriYemekleriGetir()
            withContext(Dispatchers.Main){
                favoriListesi.value = guncelListe
            }
        }
    }


    fun favorilendiMi(yemek_id:Int):Boolean{
        return favoriListesi.value?.any{
            it.yemek_id ==yemek_id
        }?: false
    }
}