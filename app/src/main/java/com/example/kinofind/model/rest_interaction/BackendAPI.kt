package com.example.kinofind.model.rest_interaction

import com.example.kinofind.BuildConfig
import com.example.kinofind.model.rest_entities.BaseActorByNameDTO
import com.example.kinofind.model.rest_entities.BaseActorDTO
import com.example.kinofind.model.rest_entities.BaseDTO
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface BackendAPI {
    @GET("movie/top_rated?api_key=${BuildConfig.KINO_API_KEY}")
    fun getTopRatedFilms(): Call<BaseDTO>

    @GET("search/person?")
    fun getActorByName(
            @Query("api_key") key: String = BuildConfig.KINO_API_KEY,
            @Query("query") query: String
    ): Call<BaseActorByNameDTO>

    @GET("person/{id}?api_key=${BuildConfig.KINO_API_KEY}")
    fun getActorById(@Path("id") id: Long): Call<BaseActorDTO>
}