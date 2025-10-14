package com.ecemm.yumico.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class AuthRepository @Inject constructor() {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // Kullanıcı bilgisi ve giriş durumu
    private val _firebaseUser = MutableLiveData<FirebaseUser?>()
    val firebaseUser: LiveData<FirebaseUser?> = _firebaseUser

    private val _isUserLoggedIn = MutableLiveData<Boolean>()
    val isUserLoggedIn: LiveData<Boolean> = _isUserLoggedIn

    // Hata mesajlarını ViewModel üzerinden göndermek için
    private val _authError = MutableLiveData<String>()
    val authError: LiveData<String> = _authError

    init {
        _firebaseUser.value = auth.currentUser
        _isUserLoggedIn.value = auth.currentUser != null
    }

    // Ortak authenticate fonksiyonu
    private fun authenticate(email: String, password: String, isLogin: Boolean) {
        val task = if (isLogin) {
            auth.signInWithEmailAndPassword(email, password)
        } else {
            auth.createUserWithEmailAndPassword(email, password)
        }

        task.addOnCompleteListener { result ->
            if (result.isSuccessful) {
                _firebaseUser.postValue(auth.currentUser)
                _isUserLoggedIn.postValue(true)
            } else {
                _authError.postValue(result.exception?.message ?: "Authentication failed")
            }
        }
    }

    // Kullanıcı girişi
    fun login(email: String, password: String) = authenticate(email, password, true)

    // Kullanıcı kaydı
    fun register(email: String, password: String) = authenticate(email, password, false)

    // Çıkış
    fun signOut() {
        auth.signOut()
        _firebaseUser.postValue(null)
        _isUserLoggedIn.postValue(false)
    }
}
