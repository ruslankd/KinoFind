package com.example.kinofind.model

import com.example.kinofind.model.entities.Film
import com.example.kinofind.model.rest_entities.BaseDTO
import retrofit2.Callback
import java.util.*

interface Repository {
    fun getTopRatedFilmsFromServerAsync(callback: Callback<BaseDTO>)
    fun getTopRatedFilmsFromServer(): AppState?
    fun getFilmsFromLocalSource(): List<Film>
}