package com.example.kinofind

import android.util.Log

object MyObjectClass {
    val myFilm: Film = Film("Iron man", 5.0)

    init {
        var printFilm = myFilm.copy("Hulk")
        Log.i("MyLog", printFilm.toString())
    }
}