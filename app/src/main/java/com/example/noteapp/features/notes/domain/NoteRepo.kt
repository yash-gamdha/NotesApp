package com.example.noteapp.features.notes.domain

import com.example.noteapp.features.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow

interface NoteRepo {
    suspend fun insertNote(note: Note)

    suspend fun updateNote(note: Note)

    suspend fun deleteNote(note: Note)

    suspend fun getNoteById(id: Int): Note

    fun getAllNotes(): Flow<List<Note>>

    fun search(searchData: String): Flow<List<Note>>
}