package com.dai.pandyapk.presentation

import android.graphics.Bitmap
import android.graphics.Camera
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import com.dai.pandyapk.core.Result
import com.dai.pandyapk.domain.camera.CameraRepo
import com.google.firebase.Timestamp


class CameraViewModel(private val repo: CameraRepo): ViewModel() {

    fun uploadPhoto(imageBitmap: Bitmap, description: String, title: String, favorite: Boolean, createdAt: Timestamp) = liveData(Dispatchers.IO) {
        emit(Result.Loading())
        try {
            emit(Result.Success(repo.uploadPhoto(imageBitmap, description, title, favorite, createdAt)))
        } catch (e: Exception) {
            emit(Result.Failure(e))
        }
    }
}

class CameraViewModelFactory(private val repo: CameraRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(CameraRepo::class.java).newInstance(repo)
    }
}