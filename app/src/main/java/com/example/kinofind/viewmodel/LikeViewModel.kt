package com.example.kinofind.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kinofind.model.AppState
import com.example.kinofind.model.database.HistoryEntity
import com.example.kinofind.model.repo.DBRepository
import com.example.kinofind.model.repo.DBRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.*

class LikeViewModel(
        private val liveDataToObserve: MutableLiveData<List<HistoryEntity>> = MutableLiveData(),
        private val dbRepository: DBRepository = DBRepositoryImpl()
) : ViewModel(), CoroutineScope by MainScope() {

    fun getLiveData() = liveDataToObserve

    fun getData() = getLikedFilms()

    private fun getLikedFilms() {
        launch(Dispatchers.IO) {
            dbRepository.getLikedFilms().let {
                liveDataToObserve.postValue(it)
            }
        }
    }


}