package com.ecemm.yumico.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ecemm.yumico.data.entity.Yemekler
import com.ecemm.yumico.data.repo.YemeklerRepository
import javax.inject.Inject

class FavorilerViewModel@Inject constructor(var yemeklerRepository: YemeklerRepository) : ViewModel() {
    val favoriListesi = MutableLiveData<List<Yemekler>>()

}