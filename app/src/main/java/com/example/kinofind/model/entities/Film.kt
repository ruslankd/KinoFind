package com.example.kinofind.model.entities

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Film(
        val name: String,
        val rating: Double,
        val year: Int,
        val description: String
) : Parcelable
