package com.example.kinofind.view.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kinofind.IMAGE_PATH
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
            tvFilmName.text = film.title
            tvYear.text = film.release_date
            tvRating.text = film.vote_average.toString()
            val uri: Uri = Uri.parse("$IMAGE_PATH${film.poster_path}")
            ivFilmImage.setImageURI(uri)
            root.setOnClickListener {
                listener.onItemViewClick(film)
            }
        }

    }
}