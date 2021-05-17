package com.example.kinofind.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kinofind.model.AppState
import com.example.kinofind.model.Repository
import com.example.kinofind.model.RepositoryImpl
import com.example.kinofind.model.entities.Film

class MainFragmentViewModel(
        private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
        private val repository: Repository = RepositoryImpl()
    ) : ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getData() = getTopRatedFilmsFromServer()

    fun setData(appState: AppState) {
        liveDataToObserve.value = AppState.Loading
        Thread {
            liveDataToObserve.postValue(appState)
        }.start()
    }

    private fun getTopRatedFilmsFromServer() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            liveDataToObserve.postValue(repository.getTopRatedFilmsFromServer())
        }.start()
    }

    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            liveDataToObserve.postValue(AppState.Success(repository.getFilmsFromLocalSource()))
        }.start()
    }
}