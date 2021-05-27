package com.example.kinofind.model.database

import androidx.room.*

@Dao
interface HistoryDao {
    @Query("SELECT * FROM HistoryEntity")
    fun all(): List<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity WHERE film LIKE :film")
    fun getDataByFilm(film: String): List<HistoryEntity>

    @Query("SELECT * FROM HistoryEntity WHERE `like` = 1")
    fun getLikedFilm(): List<HistoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: HistoryEntity)

    @Update
    fun update(entity: HistoryEntity)

    @Delete
    fun delete(entity: HistoryEntity)

    @Query("DELETE FROM HistoryEntity WHERE film = :film")
    fun deleteByFilm(film: String)
}