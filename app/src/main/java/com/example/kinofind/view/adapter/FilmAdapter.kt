package com.example.kinofind.view.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kinofind.R
import com.example.kinofind.databinding.ItemFilmBinding
import com.example.kinofind.model.OnItemViewClickListener
import com.example.kinofind.model.entities.Film

class FilmAdapter(private val filmList: List<Film>, val listener: OnItemViewClickListener) :
        RecyclerView.Adapter<FilmAdapter.ViewHolder>() {

    private lateinit var binding: ItemFilmBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ItemFilmBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(filmList[position])
    }

    override fun getItemCount() = filmList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(film: Film) = with(binding) {
            tvFilmName.text = film.name
            tvYear.text = film.year.toString()
            tvRating.text = film.rating.toString()
            ivFilmImage.setImageResource(R.drawable.ic_launcher_foreground)
            root.setOnClickListener {
                listener.onItemViewClick(film)
            }
        }

    }
}