package com.dai.pandyapk.domain.login

import com.dai.pandyapk.core.Resource
import com.dai.pandyapk.data.model.Note

interface LoginRepo {
    suspend fun getUserLogin(
        userid: String,
        email: String
    )
}