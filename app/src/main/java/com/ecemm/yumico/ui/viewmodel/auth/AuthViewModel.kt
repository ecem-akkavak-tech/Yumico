package com.ecemm.yumico.ui.viewmodel.auth

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ecemm.yumico.data.repository.AuthRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthViewModel@Inject constructor(var authRepository: AuthRepository) : ViewModel(){
    //todo: Repository’den gelen LiveData’lar doğrudan ViewModel üzerinden UI’a aktarılır -data forwarding-
    val user: LiveData<FirebaseUser?> get() = authRepository.user
    val errorMessage: LiveData<String?> get() = authRepository.errorMessage

    fun signup(email:String,password:String){
        authRepository.signUp(email, password)
    }

    fun signout(){
        authRepository.signOut()
    }
    
    fun login(email:String,password:String){
        authRepository.login(email, password)
    }

    fun isUserLoggedIn(): Boolean{
        return authRepository.isUserLoggedIn()
    }

    fun getCurrentUserEmail(): String?{
        return authRepository.getCurrentUserEmail()?.split("@")?.get(0) //"example@gmail.com" -> "example"
    }
}