package com.ecemm.yumico.ui.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ecemm.yumico.data.entity.Yemekler
import com.ecemm.yumico.data.repo.FavorilerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class FavorilerViewModel(var repo: FavorilerRepository) : ViewModel() {

    private val _favoriler = MutableStateFlow<List<Yemekler>>(emptyList())
    val favoriler = _favoriler.asStateFlow()

    init {
        viewModelScope.launch {
            repo.favorilerFlow().collect { list ->
                _favoriler.value = list
            }
        }
    }

    fun onFavoriClick(yemek: Yemekler) = viewModelScope.launch {
        val list = _favoriler.value
        val zatenVar = list.any { it.yemek_id == yemek.yemek_id }

        if (zatenVar) repo.favoriSil(yemek.yemek_id.toString())
        else repo.favoriEkle(yemek)
    }
}
