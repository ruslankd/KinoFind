package com.example.kinofind.model

import com.example.kinofind.model.entities.Film

interface OnItemViewClickListener {
    fun onItemViewClick(film: Film)
}