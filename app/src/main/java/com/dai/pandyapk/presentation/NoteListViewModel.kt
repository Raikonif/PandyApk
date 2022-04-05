package com.dai.pandyapk.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.dai.pandyapk.core.Resource
import com.dai.pandyapk.domain.noteslist.NoteListRepo
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class NoteListViewModel(private val repo: NoteListRepo) : ViewModel() {
    fun fetchLatestNotes() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(repo.getLatestNotes())
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }
    fun registerFavoriteButtonState(noteId: String, favorite: Boolean) =
        liveData(viewModelScope.coroutineContext + Dispatchers.Main){
        emit(Resource.Loading())
        kotlin.runCatching {
            repo.registerFavoriteButtonState(noteId, favorite)
        }.onSuccess {
            emit(Resource.Success(Unit))
        }.onFailure { throwable ->
            emit(Resource.Failure(Exception(throwable.message)))
        }
    }
}

class NoteListViewModelFactory(private val repo: NoteListRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(NoteListRepo::class.java).newInstance(repo)
    }

}