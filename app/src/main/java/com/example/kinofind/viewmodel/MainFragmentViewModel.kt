package com.example.kinofind.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kinofind.model.AppState
import com.example.kinofind.model.Repository
import com.example.kinofind.model.RepositoryImpl
import com.example.kinofind.model.entities.Film
import com.example.kinofind.model.rest_entities.BaseDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Error
import java.lang.IllegalStateException
import java.util.*

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

    fun loadDataAsync() {
        val callback = object : Callback<BaseDTO> {
            override fun onResponse(call: Call<BaseDTO>, response: Response<BaseDTO>) {
                if(response.isSuccessful) {
                    var films = LinkedList<Film>()
                    response.body()?.let {
                        for (res in it.results) {
                            films.add(Film(
                                    res.title,
                                    res.vote_average,
                                    res.release_date,
                                    res.overview,
                                    res.poster_path
                            ))
                        }
                    }
                    liveDataToObserve.postValue(AppState.Success(films))
                } else {
                    liveDataToObserve.postValue(AppState.Error(Error("Error")))
                }
            }

            override fun onFailure(call: Call<BaseDTO>, t: Throwable) {
                liveDataToObserve.postValue(AppState.Error(t))
            }
        }

        repository.getTopRatedFilmsFromServerAsync(callback)
    }
}