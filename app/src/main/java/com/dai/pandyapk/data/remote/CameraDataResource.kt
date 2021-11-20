package com.dai.pandyapk.data.remote

import android.graphics.Bitmap
import com.dai.pandyapk.data.model.Note
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import java.util.*

class CameraDataResource {

    suspend fun uploadPhoto(
        imageBitmap: Bitmap,
        description: String,
        title: String,
        favorite: Boolean,
        createdAt: Timestamp
    ) {
        val user = Firebase.auth.currentUser
        val randomName = UUID.randomUUID().toString()
        val imageRef = Firebase.storage.reference.child("images/notes/${user?.uid}/$randomName")

        val baos = ByteArrayOutputStream()
        imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)

        val downloadUrl = imageRef.putBytes(baos.toByteArray())
            .await().storage.downloadUrl.
            await().toString()
        user?.let {
            it.displayName?.let {displayName ->
                Firebase.firestore.collection("notes")
                    .document("${user.uid}")
                    .collection("pathology")
                    .add(Note(title = title, description = description, imgUrl = downloadUrl, favorite = favorite, createdAt = createdAt))
            }
        }
    }
}