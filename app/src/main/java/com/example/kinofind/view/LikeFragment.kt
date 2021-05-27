package com.example.kinofind.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kinofind.R
import com.example.kinofind.databinding.LikeFragmentBinding
import com.example.kinofind.model.AppState
import com.example.kinofind.model.OnItemViewClickListener
import com.example.kinofind.model.database.HistoryEntity
import com.example.kinofind.model.entities.Film
import com.example.kinofind.view.adapter.FilmAdapter
import com.example.kinofind.viewmodel.LikeViewModel
import com.example.kinofind.viewmodel.MainFragmentViewModel
import java.util.ArrayList

class LikeFragment : Fragment() {

    companion object {
        fun newInstance() = LikeFragment()
    }

    private lateinit var binding: LikeFragmentBinding
    private val viewModel: LikeViewModel by lazy {
        ViewModelProvider(this).get(LikeViewModel::class.java)
    }

    private lateinit var rvFilms: RecyclerView
    private lateinit var filmAdapter: FilmAdapter
    private var filmList = ArrayList<Film>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = LikeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Liked films"
        setupRvFilms()
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getData()
    }

    @Suppress("NAME_SHADOWING")
    private fun setupRvFilms() {

        filmAdapter = FilmAdapter(filmList, object : OnItemViewClickListener {
            override fun onItemViewClick(film: Film) {
                val manager = activity?.supportFragmentManager
                manager?.let { manager ->
                    val bundle = Bundle().apply {
                        putParcelable(MainFragment.BUNDLE_EXTRA, film)
                    }
                    manager.beginTransaction()
                            .add(R.id.container, DetailsFragment.newInstance(bundle))
                            .addToBackStack("")
                            .commitAllowingStateLoss()
                }
            }
        })

        rvFilms = binding.rvLikedFilms.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = filmAdapter
        }
    }

    private fun renderData(entities: List<HistoryEntity>) {
        setData(entities.map {
            Film(
                    it.film,
                    it.vote_average,
                    it.release_date,
                    it.overview,
                    it.poster_path
            )
        })
    }

    private fun setData(filmData: List<Film>) {
        filmList.clear()
        filmList.addAll(filmData)
        filmAdapter.notifyDataSetChanged()
    }

}