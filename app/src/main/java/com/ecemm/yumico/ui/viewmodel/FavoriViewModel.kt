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


    fun favoriYemekleriGetir(user_id:String){
        CoroutineScope(Dispatchers.Main).launch {
            favoriListesi.value = favoriRepository.favoriYemekleriGetir(user_id)
        }
    }

    fun favoriEkle(user_id:String , yemek_adi:String , yemek_resim_adi:String , yemek_fiyat:Int , rating:Float){
        //fe & be kısımları güncel olmalı
        CoroutineScope(Dispatchers.Main).launch {
            //aynı yemeği tekrar tekrar favorilere eklememek için:
            val mevcutFavoriler = favoriRepository.favoriYemekleriGetir(user_id)

            if (mevcutFavoriler.any { it.yemek_adi == yemek_adi }) {
                // zaten var, ekleme yapma
                return@launch
            }
            favoriRepository.favoriEkle(user_id, yemek_adi, yemek_resim_adi, yemek_fiyat, rating)

            val guncelListe = favoriRepository.favoriYemekleriGetir(user_id)

            withContext(Dispatchers.Main){
                favoriListesi.value = guncelListe //ekleme işleminden sonra ilgili sayfanın ui'ı güncellensin
            }
        }
    }

    fun favoriSil(yemek_id: Int ,user_id: String) {
        //fe & be kısımları güncel olmalı
            viewModelScope.launch(Dispatchers.IO) {
                favoriRepository.favoriSil(yemek_id, user_id)

                //silme işleminden sonra ui'daki liste güncellenir
                val guncelListe = favoriRepository.favoriYemekleriGetir(user_id)
                withContext(Dispatchers.Main) {
                    favoriListesi.value = guncelListe
                }

        }
    }

    fun favoriRatingGuncelle(yemek_id: Int, user_id: String ,rating: Float) {

            viewModelScope.launch(Dispatchers.IO) {
                favoriRepository.favoriRatingGuncelle(yemek_id , user_id ,rating)
                val guncelListe = favoriRepository.favoriYemekleriGetir(user_id)
                withContext(Dispatchers.Main) {
                    favoriListesi.value = guncelListe
                }

        }
    }
}