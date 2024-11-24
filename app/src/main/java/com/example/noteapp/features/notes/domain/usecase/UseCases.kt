package com.example.noteapp.features.notes.domain.usecase

data class UseCases(
    val insertNote: InsertNote,
    val updateNote: UpdateNote,
    val deleteNote: DeleteNote,
    val getNoteById: GetNoteById,
    val getAllNotes: GetAllNotes,
    val search: Search
)
