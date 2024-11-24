package com.example.noteapp.di

import android.content.Context
import androidx.room.Room
import com.example.noteapp.features.notes.data.LocalDatabase
import com.example.noteapp.features.notes.data.NoteRepoImpl
import com.example.noteapp.features.notes.domain.NoteRepo
import com.example.noteapp.features.notes.domain.usecase.DeleteNote
import com.example.noteapp.features.notes.domain.usecase.GetAllNotes
import com.example.noteapp.features.notes.domain.usecase.GetNoteById
import com.example.noteapp.features.notes.domain.usecase.InsertNote
import com.example.noteapp.features.notes.domain.usecase.Search
import com.example.noteapp.features.notes.domain.usecase.UpdateNote
import com.example.noteapp.features.notes.domain.usecase.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideLocalDatabase(@ApplicationContext context: Context): LocalDatabase =
        Room.databaseBuilder(
            context = context,
            klass = LocalDatabase::class.java,
            name = "localDB_notes"
        ).fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun provideNoteRepo(db: LocalDatabase): NoteRepo {
        return NoteRepoImpl(noteDao = db.noteDao())
    }

    @Provides
    @Singleton
    fun provideUseCases(repo: NoteRepo): UseCases {
        return UseCases(
            insertNote = InsertNote(repo),
            updateNote = UpdateNote(repo),
            deleteNote = DeleteNote(repo),
            getNoteById = GetNoteById(repo),
            getAllNotes = GetAllNotes(repo),
            search = Search(repo)
        )
    }
}