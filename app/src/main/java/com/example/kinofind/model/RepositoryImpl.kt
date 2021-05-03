package com.example.kinofind.model

import com.example.kinofind.model.entities.Film
import java.util.*

class RepositoryImpl : Repository {
    override fun getFilmsFromLocalSource(): List<Film> =
        listOf(
                Film("Hulk", 6.2, 2003),
                Film("Intouchables", 8.8, 2011),
                Film("Green Mile", 8.9, 1999)
                )

}