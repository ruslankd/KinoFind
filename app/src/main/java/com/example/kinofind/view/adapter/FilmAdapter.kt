package com.example.kinofind.view.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kinofind.databinding.ItemFilmBinding
import com.example.kinofind.model.entities.Film

class FilmAdapter(val filmList: List<Film>) : RecyclerView.Adapter<FilmAdapter.ViewHolder>() {

    private lateinit var binding: ItemFilmBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val film = filmList[position]

        holder.tvFilmName.text = film.name
        holder.tvFilmYear.text = film.year.toString()
        holder.tvFilmRating.text = film.rating.toString()
    }

    override fun getItemCount() = filmList.size

    class ViewHolder(binding: ItemFilmBinding) : RecyclerView.ViewHolder(binding.root) {
        val tvFilmName: TextView = binding.tvFilmName
        val tvFilmYear: TextView = binding.tvYear
        val tvFilmRating: TextView = binding.tvRating

    }
}