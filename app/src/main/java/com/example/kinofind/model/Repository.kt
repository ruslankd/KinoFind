package com.example.kinofind.model

import com.example.kinofind.model.entities.Film
import com.example.kinofind.model.rest_entities.BaseDTO
import java.util.*

interface Repository {
    fun getTopRatedFilmsFromServer(): AppState?
    fun getFilmsFromLocalSource(): List<Film>
}