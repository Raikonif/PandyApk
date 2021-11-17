package com.dai.pandyapk.model

import java.util.*

class Note (
    val id: String = "",
    val title: String= "",
    val description: String = "",
    val imgUrl: String = "",
    val favorite: Boolean = false,
    val createdAt: Date = Date()
)