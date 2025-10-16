package com.ecemm.yumico.data.repository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(private val firebaseAuth:FirebaseAuth) {
    private val _user = MutableLiveData<FirebaseUser?>() //güncellenebilen liste
    val user: LiveData<FirebaseUser?> get() = _user      //encapsulation sayesinde sadece diğer classlarca okunabilen liste

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage

    //Signup işlemi
    fun signUp(email: String, password: String) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _user.value = firebaseAuth.currentUser //_user.value güncellenir, yani LiveData üzerinden UI bilgilendirilir.
                } else {
                    _errorMessage.value = task.exception?.message
                }
            }
    }

    //Signout işlemi
    fun signOut() {
        firebaseAuth.signOut()
        _user.value = null //_user.value’ı null yapar, yani kullanıcı artık giriş yapmamış olarak görünür oturum kapanır
    }


}
/*Hatırlatma
 1) @ViewModelScoped: Bu annotation Hilt (Dependency Injection) yapısından gelir.
 2) @Inject constructor(...): Hilt’in FirebaseAuth nesnesini otomatik olarak bu sınıfa enjekte etmesini sağlar.

 Yani  AuthRepository oluştururken, FirebaseAuth’u manuel olarak vermemize gerek kalmaz.

 3) _user → içerde güncellenebilir bir MutableLiveData.
 4) user → dışarıya sadece okunabilir LiveData olarak açılmış hali.
 Bu yapı “encapsulation (kapsülleme)” prensibidir. ViewModel veya Fragment sadece gözlemler ama değiştiremez.
*/