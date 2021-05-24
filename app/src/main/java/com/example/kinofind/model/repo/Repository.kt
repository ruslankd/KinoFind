package com.example.kinofind.model.repo

import com.example.kinofind.model.AppState
import com.example.kinofind.model.entities.Film
import com.example.kinofind.model.rest_entities.BaseDTO
import retrofit2.Callback

interface Repository {
    fun getTopRatedFilmsFromServerAsync(callback: Callback<BaseDTO>)
    fun getTopRatedFilmsFromServer(): AppState?
    fun getFilmsFromLocalSource(): List<Film>
}