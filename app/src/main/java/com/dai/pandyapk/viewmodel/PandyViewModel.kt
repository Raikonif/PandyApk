package com.dai.pandyapk.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dai.pandyapk.model.Note

class PandyViewModel: ViewModel() {
    val pandymodel = MutableLiveData<Note>()

    fun listenerNote(){
//        val currentNote: Note = NewNoteActivity::class.java.

    }
}

