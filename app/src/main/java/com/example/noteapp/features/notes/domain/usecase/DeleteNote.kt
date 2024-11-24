package com.example.noteapp.features.notes.domain.usecase

import com.example.noteapp.features.notes.domain.NoteRepo
import com.example.noteapp.features.notes.domain.model.Note
import javax.inject.Inject

class DeleteNote @Inject constructor(
    private val repo: NoteRepo
) {
    suspend operator fun invoke(note: Note) =
        repo.deleteNote(note = note)
}