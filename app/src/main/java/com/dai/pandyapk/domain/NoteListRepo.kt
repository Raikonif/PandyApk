package com.dai.pandyapk.domain

import com.dai.pandyapk.core.Resource
import com.dai.pandyapk.data.model.Note

interface NoteListRepo {
    suspend fun getLatestNotes(): Resource <List<Note>>
}