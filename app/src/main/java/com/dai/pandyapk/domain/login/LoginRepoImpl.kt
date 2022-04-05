package com.dai.pandyapk.domain.login

import android.graphics.Bitmap
import com.dai.pandyapk.data.remote.CameraDataResource
import com.dai.pandyapk.data.remote.LoginDatatResource
import com.dai.pandyapk.domain.camera.CameraRepo
import com.google.firebase.Timestamp

class LoginRepoImpl(private val dataSource: LoginDatatResource): LoginRepo{
    override suspend fun getUserLogin(
        userid: String,
        email: String,
    ) {
        dataSource.getUserLogin( userid, email)
    }
}