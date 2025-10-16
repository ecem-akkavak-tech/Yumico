package com.ecemm.yumico.ui.viewmodel.auth

import androidx.lifecycle.ViewModel
import com.ecemm.yumico.data.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel@Inject constructor(var authRepository: AuthRepository) : ViewModel(){

}