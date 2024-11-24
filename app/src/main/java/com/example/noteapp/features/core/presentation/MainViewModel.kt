package com.example.noteapp.features.core.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.noteapp.features.notes.domain.model.Note
import com.example.noteapp.features.notes.domain.usecase.UseCases
import com.example.noteapp.util.Response
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCases: UseCases
) : ViewModel() {
    var note by mutableStateOf(
        Note(id = 0, title = "", description = null, isBookmarked = false)
    )

    var deletedNote: Note? = null

    private val _response = MutableStateFlow<Response<List<Note>>>(Response.Loading)
    val response = _response.asStateFlow()

    private var _searchResponse = MutableStateFlow<Response<List<Note>>>(Response.Loading)
    val searchResponse = _searchResponse.asStateFlow()

    init {
        getAllNotes()
    }

    private fun getAllNotes() = viewModelScope.launch {
        useCases.getAllNotes()
            .onStart {
                _response.value = Response.Loading
            }.catch { error ->
                _response.value = Response.Error(error)
            }.collect { notesList ->
                _response.value = Response.Success(data = notesList)
            }
    }

    fun insertNote(note: Note) {
        viewModelScope.launch {
            useCases.insertNote(note = note)
        }
    }

    fun updateNote(note: Note) {
        viewModelScope.launch {
            useCases.updateNote(note = note)
        }
    }

    fun deleteNote(note: Note) {
        viewModelScope.launch {
            deletedNote = note
            useCases.deleteNote(note = note)
        }
    }

    fun undoDeletedNote() {
        viewModelScope.launch {
            deletedNote?.let { deletedNote ->
                useCases.insertNote(note = deletedNote)
            }
        }
    }

    fun search(searchData: String) = viewModelScope.launch {
        useCases.search(searchData)
            .onStart {
                _searchResponse.value = Response.Loading
            } .catch { error ->
                _searchResponse.value = Response.Error(error = error)
            } .collect { notes ->
                _searchResponse.value = Response.Success(data = notes)
            }
    }

    fun getNoteById(noteId: Int) {
        viewModelScope.launch {
            note = useCases.getNoteById(noteId = noteId)
        }
    }

    fun updateNoteTitle(newTitle: String) {
        note = note.copy(
            title = newTitle
        )
    }

    fun updateNoteDescription(newDescription: String) {
        note = note.copy(
            description = newDescription
        )
    }

    fun updateNoteBookMarked(newBookmarked: Boolean) {
        note = note.copy(
            isBookmarked = newBookmarked
        )
    }
}