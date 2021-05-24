package com.example.kinofind.model.repo

import com.example.kinofind.model.database.HistoryEntity
import com.example.kinofind.model.entities.Film

interface DBRepository {
    fun getLikedFilms(): List<HistoryEntity>
    fun saveEntity(entity: HistoryEntity)
    fun getEntity(film: String): HistoryEntity?
}