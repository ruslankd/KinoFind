package com.example.kinofind.model.rest_entities

import com.google.gson.annotations.SerializedName

data class BaseActorDTO(
        @SerializedName("place_of_birth")
        val placeOfBirth: String,
)
