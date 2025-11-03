package com.ecemm.yumico.data.repo
import com.ecemm.yumico.data.entity.Yemekler
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Singleton

@Singleton
class FavorilerRepository(var firestore: FirebaseFirestore , var auth: FirebaseAuth) {

    private fun userFavorilerCollection(): CollectionReference? {
        val uid = auth.currentUser?.uid
        return uid?.let {
            firestore.collection("users").document(it).collection("favoriler")
        }
    }

    suspend fun favoriEkle(yemek: Yemekler) {
        val col = userFavorilerCollection() ?: return
        col.document(yemek.yemek_id.toString()).set(yemek).await()
    }

    suspend fun favoriSil(yemekId: String) {
        val col = userFavorilerCollection() ?: return
        col.document(yemekId).delete().await()
    }

    fun favorilerFlow(): Flow<List<Yemekler>> = callbackFlow {
        val col = userFavorilerCollection() ?: run {
            trySend(emptyList())
            close()
            return@callbackFlow
        }

        val reg = col.addSnapshotListener { snap, _ ->
            val list = snap?.toObjects(Yemekler::class.java) ?: emptyList()
            trySend(list)
        }
        awaitClose { reg.remove() }
    }
}
