package com.dai.pandyapk.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dai.pandyapk.model.Note
import com.dai.pandyapk.view.notes.NewNoteActivity

class PandyViewModel: ViewModel() {
    val pandymodel = MutableLiveData<Note>()

    fun listenerNote(){
//        val currentNote: Note = NewNoteActivity::class.java.

    }
}

