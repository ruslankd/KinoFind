package com.example.kinofind.model.repo

import com.example.kinofind.model.database.Database
import com.example.kinofind.model.database.HistoryEntity
import com.example.kinofind.model.entities.Film
class DBRepositoryImpl : DBRepository {

    override fun getLikedFilms(): List<HistoryEntity> =
            Database.db.historyDao().getLikedFilm()


    override fun saveEntity(entity: HistoryEntity) {
        Database.db.historyDao().insert(entity)
    }

    override fun getEntity(film: String): HistoryEntity? {
        val entities = Database.db.historyDao().getDataByFilm(film)
        return if (entities.isNotEmpty()) {
            entities[0]
        } else {
            null
        }
    }


    private fun convertHistoryEntityToFilm(entityList: List<HistoryEntity>): List<Film> =
            entityList.map {
                Film(it.film, it.vote_average, it.release_date, it.overview, it.poster_path)
            }



}

