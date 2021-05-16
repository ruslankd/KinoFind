package com.example.kinofind.model.rest_entities

data class BaseDTO(
        val page : Int,
        val results : List<Results>,
        val total_pages : Int,
        val total_results : Int
)
