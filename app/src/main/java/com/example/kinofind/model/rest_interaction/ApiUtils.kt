package com.example.kinofind.model.rest_interaction

import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

private const val TIMEOUT = 10L
object ApiUtils {
    private const val baseUrlMainPart = "https://api.themoviedb.org/"
    private const val baseUrlVersion = "3/"
    const val baseUrl = "$baseUrlMainPart$baseUrlVersion"

    fun getOkHTTPBuilderWithHeaders(): OkHttpClient {
        val httpClient = OkHttpClient.Builder()
        httpClient.connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        httpClient.readTimeout(TIMEOUT, TimeUnit.SECONDS)
        httpClient.writeTimeout(TIMEOUT, TimeUnit.SECONDS)
        httpClient.addInterceptor {
            val original = it.request()
            val request = original.newBuilder()
                    .method(original.method(), original.body())
                    .build()

            it.proceed(request)
        }

        return httpClient.build()
    }
}