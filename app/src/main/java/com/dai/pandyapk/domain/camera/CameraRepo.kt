package com.dai.pandyapk.domain.camera

import android.graphics.Bitmap
import com.google.firebase.Timestamp

interface CameraRepo {
    suspend fun uploadPhoto(
        imageBitmap: Bitmap,
        description: String,
        title: String,
        favorite: Boolean,
        createdAt: Timestamp
    )
}