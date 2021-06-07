package com.example.kinofind.model.rest_entities

data class Results (
        val adult : Boolean,
        val overview : String,
        val poster_path : String,
        val release_date : String,
        val title : String,
        val vote_average : Double,
)
