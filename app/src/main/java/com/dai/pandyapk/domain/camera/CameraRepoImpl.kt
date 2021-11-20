package com.dai.pandyapk.domain.camera

import android.graphics.Bitmap
import com.dai.pandyapk.data.remote.CameraDataResource
import com.google.firebase.Timestamp
import java.sql.Time

class CameraRepoImpl(private val dataSource: CameraDataResource): CameraRepo {
    override suspend fun uploadPhoto(
        imageBitmap: Bitmap,
        description: String,
        title: String,
        favorite: Boolean,
        createdAt: Timestamp
    ) {
        dataSource.uploadPhoto( imageBitmap, description, title, favorite, createdAt)
    }
}