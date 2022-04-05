package com.dai.pandyapk.domain.noteslist

import com.dai.pandyapk.core.Resource
import com.dai.pandyapk.data.model.Note

interface NoteListRepo {
    suspend fun getLatestNotes(): Resource <List<Note>>
    suspend fun registerFavoriteButtonState(noteId: String, favorite: Boolean){

    }
}