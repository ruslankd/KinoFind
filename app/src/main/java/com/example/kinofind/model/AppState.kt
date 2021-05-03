package com.example.kinofind.model

import com.example.kinofind.model.entities.Film

sealed class AppState {
    data class Success(val filmData: List<Film>) : AppState()
    data class Error(val error: Throwable) : AppState()
    object Loading : AppState()
}
