package com.example.kinofind

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private lateinit var myFilm: Film

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myFilm = Film("1+1", 4.9)
        var myObject = MyObjectClass.myFilm

        setupBtnClick()

        val array = arrayListOf<String>("a", "b", "c")
        for (i in 0 until 2) {
            Log.i("MyLog", array[i])
        }
    }

    private fun setupBtnClick() {
        val btn = findViewById<Button>(R.id.btn_click)

        btn.setOnClickListener {
            Toast.makeText(
                    this,
                    "Film: ${myFilm.name} (${myFilm.rating})",
                    Toast.LENGTH_SHORT).show()
        }
    }
}