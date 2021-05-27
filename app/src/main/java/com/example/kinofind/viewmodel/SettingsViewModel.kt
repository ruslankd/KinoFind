package com.example.kinofind.viewmodel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.kinofind.App
import com.example.kinofind.model.AppState

class SettingsViewModel(
        private val liveDataToObserve: MutableLiveData<Boolean> = MutableLiveData(),
        private val sp: SharedPreferences? = App.appInstance.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
) : ViewModel() {

    fun getLiveData() = liveDataToObserve

    fun getData() = getState18()

    private fun getState18() {
        liveDataToObserve.value = sp?.getBoolean(STATE_18, false) ?: false
    }

    fun setData(state18: Boolean) {
        Thread {
            sp?.edit()?.putBoolean(STATE_18, state18)?.apply()
        }.start()
    }

    companion object {
        const val SP_NAME = "MainSP"
        const val STATE_18 = "state 18"
    }
}