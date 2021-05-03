package com.example.kinofind.model

import com.example.kinofind.model.entities.Film
import java.util.*

interface Repository {
    fun getFilmsFromLocalSource(): List<Film>
}