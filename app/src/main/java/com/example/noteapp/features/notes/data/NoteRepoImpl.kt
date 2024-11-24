package com.example.noteapp.features.notes.data

import com.example.noteapp.features.notes.domain.NoteRepo
import com.example.noteapp.features.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NoteRepoImpl @Inject constructor(
    private val noteDao: NoteDao
): NoteRepo {
    override suspend fun insertNote(note: Note) =
        noteDao.insertNote(note = note)

    override suspend fun updateNote(note: Note) =
        noteDao.updateNote(note = note)

    override suspend fun deleteNote(note: Note) =
        noteDao.deleteNote(note = note)

    override suspend fun getNoteById(id: Int): Note {
        return noteDao.getNoteById(id = id)
    }

    override fun getAllNotes(): Flow<List<Note>> {
        return noteDao.getAllNotes()
    }

    override fun search(searchData: String): Flow<List<Note>> {
        return noteDao.search(searchData)
    }
}