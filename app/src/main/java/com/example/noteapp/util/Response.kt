package com.example.noteapp.util

// response interface
sealed interface Response<out T> {
    data object Idle: Response<Nothing>
    data object Loading: Response<Nothing>
    data class Success<out T>(val data: T): Response<T>
    data class Error(val error: Throwable): Response<Nothing>
}