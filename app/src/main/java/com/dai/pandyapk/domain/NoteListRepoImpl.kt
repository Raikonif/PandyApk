package com.dai.pandyapk.domain

import com.dai.pandyapk.core.Resource
import com.dai.pandyapk.data.model.Note
import com.dai.pandyapk.data.remote.NoteListDataSource

class NoteListRepoImpl(private val dataSource: NoteListDataSource): NoteListRepo {

    override suspend fun getLatestNotes(): Resource<List<Note>> = dataSource.getLatestNotes()
}