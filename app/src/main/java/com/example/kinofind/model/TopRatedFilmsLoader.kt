package com.example.kinofind.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.kinofind.BuildConfig
import com.example.kinofind.model.entities.Film
import com.example.kinofind.model.rest_entities.BaseDTO
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.MalformedURLException
import java.net.URL
import java.util.*
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

private const val REQUEST_GET = "GET"
private const val REQUEST_TIMEOUT = 10000

object TopRatedFilmsLoader {
    fun loadFilms(): AppState? {
        try {
            val uri = URL("https://api.themoviedb.org/3/movie/top_rated?api_key=${BuildConfig.KINO_API_KEY}")

            lateinit var urlConnection: HttpsURLConnection
            try {
                urlConnection = uri.openConnection() as HttpsURLConnection
                urlConnection.requestMethod = REQUEST_GET
                urlConnection.readTimeout = REQUEST_TIMEOUT
                val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
                val lines = if(Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                    getLinesForOld(bufferedReader)
                } else {
                    getLines(bufferedReader)
                }
                val dto = Gson().fromJson(lines, BaseDTO::class.java)
                var films = LinkedList<Film>()
                dto?.let {
                    for (res in dto.results) {
                        films.add(Film(
                                res.title,
                                res.vote_average,
                                res.release_date,
                                res.overview,
                                res.poster_path
                        ))
                    }
                }
                return AppState.Success(films)
            } catch (e: Exception) {
                return AppState.Error(e)
            } finally {
                urlConnection.disconnect()
            }
        } catch (e: MalformedURLException) {
            return AppState.Error(e)
        }
    }

    private fun getLinesForOld(reader: BufferedReader): String {
        val rawData = StringBuilder(1024)
        var tempVariable: String?

        while (reader.readLine().also { tempVariable = it } != null) {
            rawData.append(tempVariable).append("\n")
        }

        reader.close()
        return rawData.toString()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(reader: BufferedReader): String {
        return reader.lines().collect(Collectors.joining("\n"))
    }
}