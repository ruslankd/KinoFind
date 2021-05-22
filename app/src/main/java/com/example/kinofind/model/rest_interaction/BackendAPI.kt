package com.example.kinofind.model.rest_interaction

import com.example.kinofind.BuildConfig
import com.example.kinofind.model.rest_entities.BaseDTO
import retrofit2.Call
import retrofit2.http.GET

interface BackendAPI {
    @GET("top_rated?api_key=${BuildConfig.KINO_API_KEY}")
    fun getTopRatedFilms(): Call<BaseDTO>
}