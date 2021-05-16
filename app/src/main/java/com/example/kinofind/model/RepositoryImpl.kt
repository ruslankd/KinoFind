package com.example.kinofind.model

import com.example.kinofind.model.entities.Film
import java.util.*

class RepositoryImpl : Repository {
    override fun getTopRatedFilmsFromServer(): AppState? = TopRatedFilmsLoader.loadFilms()

    override fun getFilmsFromLocalSource(): List<Film> =
        listOf(
                Film(
                        "Hulk",
                        6.2,
                        "2003",
                        "Bruce Banner, a genetics researcher with a tragic past," +
                                "suffers an accident that causes him to transform into a " +
                                "raging green monster when he gets angry."
                ),
                Film(
                        "Intouchables",
                        8.8,
                        "2011",
                        "After he becomes a quadriplegic from a paragliding accident," +
                                " an aristocrat hires a young man from the projects to be his caregiver."
                ),
                Film(
                        "Green Mile",
                        8.9,
                        "1999",
                        "The lives of guards on Death Row are affected by one of their charges:" +
                                " a black man accused of child murder and rape, yet who has a mysterious gift."
                )
                )

}