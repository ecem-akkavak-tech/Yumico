package com.ecemm.yumico.data.repository
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ecemm.yumico.data.entity.Users
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepository @Inject constructor(private val firebaseAuth:FirebaseAuth) {
    private val _user = MutableLiveData<FirebaseUser?>() //güncellenebilen liste
    val user: LiveData<FirebaseUser?> get() = _user      //encapsulation sayesinde sadece diğer classlarca okunabilen liste

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> get() = _errorMessage  //encapsulation sayesinde sadece diğer classlarca okunabilen liste

    private val firestore = FirebaseFirestore.getInstance()

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

    //Login işlemi
    fun login(email:String , password:String){
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    _user.value = firebaseAuth.currentUser
                }else{
                    _errorMessage.value = task.exception?.message
                }
        }
    }


    //todo- Session check - current user
    //Kullanıcı login olduysa currentUser boş değil demektir
    fun isUserLoggedIn(): Boolean{
        return firebaseAuth.currentUser != null
    }

    fun getCurrentUserEmail(): String? {
        return firebaseAuth.currentUser?.email
    }


    //todo- Firestore create
    fun saveUserToFirestore(user: Users, onResult: (Boolean, String?) -> Unit){
       val currentUser = firebaseAuth.currentUser
       Log.e("current user:","${currentUser?.email}")
        if(currentUser ==null){
            onResult(false, "Kullanıcı oturumu bulunamadı.")
            return
        }

        user.userId = currentUser.uid             // *böylece oturumdaki o anki kullanıcının idsini Users objesine eklemiş olduk

        firestore.collection("users") // users tablosu oluşmuş olur
            .document(currentUser.uid)
            .set(user)
            .addOnSuccessListener {
                onResult(true, null)
            }
            .addOnFailureListener { e ->
                onResult(false, e.message)
            }
    }




}
/*Hatırlatma
 1) @ViewModelScoped: Bu annotation Hilt (Dependency Injection) yapısından gelir.
 2) @Inject constructor(...): Hilt’in FirebaseAuth nesnesini otomatik olarak bu sınıfa enjekte etmesini sağlar.
 Yani  AuthRepository oluştururken, FirebaseAuth’u manuel olarak vermemize gerek kalmaz.
 3) _user → içerde güncellenebilir bir MutableLiveData.
 4) user → dışarıya sadece okunabilir LiveData olarak açılmış hali.
 Bu yapı “encapsulation (kapsülleme)” prensibidir. ViewModel veya Fragment sadece gözlemler ama değiştiremez.

** NOT **
*Eğer permissions için sorun çıkarsa*
Firebase Console → Firestore → Rules sekmesine git.*****
Test için rules’u şöyle değiştir:

service cloud.firestore {
  match /databases/{database}/documents {
    match /users/{userId} {
      allow read, write: if request.auth != null;
    }
  }
}
*/