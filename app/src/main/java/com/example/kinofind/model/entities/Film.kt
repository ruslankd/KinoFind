package com.example.kinofind.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Film(
        val title: String,
        val vote_average: Double,
        val release_date: String,
        val overview: String,
        val poster_path: String
) : Parcelable
