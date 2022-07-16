package com.dai.pandyapk.data.remote

import com.dai.pandyapk.core.Resource
import com.dai.pandyapk.data.model.Note
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await


class NoteListDataSource() {

    suspend fun getLatestNotes(): Resource<List<Note>>{
        val uid = Firebase.auth.uid
        val noteListData = mutableListOf<Note>()
        val querySnapshot = Firebase.firestore
            .collection("notes")
            .document("$uid")
            .collection("pathology")
            .get()
            .await()


        for(note in querySnapshot.documents){
            note.toObject(Note::class.java)?.let { fbNote ->
                noteListData.add(fbNote)
            }

        }
        return Resource.Success(noteListData)
    }

    fun registerFavoriteButtonState(noteId: String, favorite: Boolean) {

        val uid = Firebase.auth.currentUser?.uid
        val postRef = FirebaseFirestore.getInstance().collection("notes").document(noteId)
        //TODO: logica para cambiar el estado del boton favorite
    }
}