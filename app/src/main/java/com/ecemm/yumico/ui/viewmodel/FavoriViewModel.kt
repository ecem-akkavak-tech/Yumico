package com.ecemm.yumico.ui.viewmodel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ecemm.yumico.data.entity.FavoriYemek
import com.ecemm.yumico.data.repo.FavoriRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriViewModel  @Inject constructor (var favoriRepository: FavoriRepository): ViewModel(){

    val favoriListesi = MutableLiveData<List<FavoriYemek>>()

    fun favoriYemekleriGetir(){
        CoroutineScope(Dispatchers.Main).launch {
            favoriListesi.value = favoriRepository.favoriYemekleriGetir()
        }
    }

    fun favoriEkle(yemek_adi:String,yemek_resim_adi:String,yemek_fiyat:Int){
        CoroutineScope(Dispatchers.Main).launch { //main arayüzle ilgili işlemlerde kullanılır
            favoriRepository.favoriEkle(yemek_adi, yemek_resim_adi, yemek_fiyat)
        }
    }

    fun favoriSil(yemek_id:Int){
        CoroutineScope(Dispatchers.Main).launch {
            favoriRepository.favoriSil(yemek_id)
        }
    }
}