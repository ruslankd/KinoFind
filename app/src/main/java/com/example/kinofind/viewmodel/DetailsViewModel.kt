package com.example.kinofind.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kinofind.model.AppState
import com.example.kinofind.model.database.HistoryEntity
import com.example.kinofind.model.entities.Film
import com.example.kinofind.model.repo.DBRepository
import com.example.kinofind.model.repo.DBRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class DetailsViewModel(
        private val liveDataToObserve: MutableLiveData<HistoryEntity> = MutableLiveData(),
        private val dbRepository: DBRepository = DBRepositoryImpl()
) : ViewModel(), CoroutineScope by MainScope() {

    fun getLiveData() = liveDataToObserve

    fun getData(film: String) {
        launch(Dispatchers.IO) {
            dbRepository.getEntity(film).let {
                liveDataToObserve.postValue(it)
            }
        }
    }

    fun setData(film: Film, note: String, liked: Boolean) {
        launch(Dispatchers.IO) {
            dbRepository.saveEntity(
                    HistoryEntity(
                            film.title,
                            film.vote_average,
                            film.release_date,
                            film.overview,
                            film.poster_path,
                            note,
                            liked
                    )
            )
        }
    }
}