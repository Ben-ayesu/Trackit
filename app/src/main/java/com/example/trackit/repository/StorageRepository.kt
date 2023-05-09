package com.example.trackit.repository

import com.example.trackit.models.Notes
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

const val Trackit_COLLECTION_REF = "Trackit"

class StorageRepository() {

    fun user() = Firebase.auth.currentUser
    fun hasUser(): Boolean = Firebase.auth.currentUser != null

    fun getUserId(): String = Firebase.auth.currentUser?.uid.orEmpty()

    private val trackItRef: CollectionReference = Firebase
        .firestore.collection(Trackit_COLLECTION_REF)

    fun getUserNotes(
        userId: String
    ): Flow<Resources<List<Notes>>> = callbackFlow {
        var snapshotStateListener: ListenerRegistration? = null

        try {
            snapshotStateListener = trackItRef
                .orderBy("timestamp")
                .whereEqualTo("userId", userId)
                .addSnapshotListener { snapshot, e ->
                    val response = if (snapshot != null) {
                        val notes = snapshot.toObjects(Notes::class.java)
                        Resources.Success(data = notes)
                    } else {
                        Resources.Error(throwable = e?.cause)
                    }
                    trySend(response)
                }
        } catch (e: Exception) {
            trySend(Resources.Error(e.cause))
            e.printStackTrace()
        }
        awaitClose {
            snapshotStateListener?.remove()
        }
    }

    fun getNote(
        noteId: String,
        onError: (Throwable?) -> Unit,
        onSuccess: (Notes?) -> Unit
    ) {
        trackItRef
            .document(noteId)
            .get()
            .addOnSuccessListener {
                onSuccess.invoke(it?.toObject(Notes::class.java))
            }
            .addOnFailureListener { result ->
                onError.invoke(result.cause)
            }
    }

    fun addNote(
        userId: String,
        title: String,
        description: String,
        timestamp: Timestamp,
        color: Int = 0,
        onComplete: (Boolean) -> Unit
    ) {
        val documentId = trackItRef.document().id
        val note =
            Notes(
                userId,
                title,
                description,
                timestamp,
                colorIndex = color,
                documentId = documentId
            )

        trackItRef
            .document(documentId)
            .set(note)
            .addOnCompleteListener { result ->
                onComplete.invoke(result.isSuccessful)
            }
    }

    fun deleteNote(
        noteId: String,
        onComplete: (Boolean) -> Unit
    ) {
        trackItRef.document(noteId)
            .delete()
            .addOnCompleteListener {
                onComplete.invoke(it.isSuccessful)
            }
    }

    fun updateNote(
        noteId: String,
        title: String,
        note: String,
        color: Int,
        onResult: (Boolean) -> Unit
    ) {
        val updateDate = hashMapOf<String, Any>(
            "colotIndex" to color,
            "description" to note,
            title to title
        )
        trackItRef.document(noteId)
            .update(updateDate)
            .addOnCompleteListener {
                onResult(it.isSuccessful)
            }

    }

    fun signOut() = Firebase.auth.signOut()
}

sealed class Resources<T>(
    val data: T? = null,
    val throwable: Throwable? = null
) {
    class Loading<T> : Resources<T>()
    class Success<T>(data: T?) : Resources<T>(data = data)
    class Error<T>(throwable: Throwable?) : Resources<T>(throwable = throwable)
}