package com.example.noteapp.features.notes.domain.usecase

import com.example.noteapp.features.notes.domain.NoteRepo
import com.example.noteapp.features.notes.domain.model.Note
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllNotes @Inject constructor(
    private val repo: NoteRepo
) {
    operator fun invoke(): Flow<List<Note>> {
        return repo.getAllNotes()
    }
}