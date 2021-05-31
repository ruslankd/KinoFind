package com.example.kinofind.model.rest_entities

data class BaseActorByNameDTO(
        val page: Long,
        val results: List<Result>,
        val totalPages: Long,
        val totalResults: Long
)
