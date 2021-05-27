package com.example.kinofind.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kinofind.App
import com.example.kinofind.model.AppState
import com.example.kinofind.model.repo.Repository
import com.example.kinofind.model.repo.RepositoryImpl
import com.example.kinofind.model.entities.Film
import com.example.kinofind.model.rest_entities.BaseDTO
import com.example.kinofind.viewmodel.SettingsViewModel.Companion.STATE_18
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Error
import java.util.*

class MainFragmentViewModel(
        private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
        private val repository: Repository = RepositoryImpl()
    ) : ViewModel() {

    private val adult: Boolean =
            App.appInstance.getSharedPreferences(SettingsViewModel.SP_NAME, Context.MODE_PRIVATE)
                    .getBoolean(STATE_18, false)

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
                    val films = LinkedList<Film>()
                    response.body()?.let {
                        for (res in it.results) {
                            if (!adult && res.adult) {
                                continue
                            }
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