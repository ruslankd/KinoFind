package com.example.kinofind.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class HistoryEntity(
        @PrimaryKey() val film: String,
        val vote_average: Double,
        val release_date: String,
        val overview: String,
        val poster_path: String,
        val note: String,
        val like: Boolean
)