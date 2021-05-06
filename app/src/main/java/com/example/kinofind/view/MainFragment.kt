package com.example.kinofind.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kinofind.R
import com.example.kinofind.databinding.FragmentMainBinding
import com.example.kinofind.model.AppState
import com.example.kinofind.model.OnItemViewClickListener
import com.example.kinofind.model.entities.Film
import com.example.kinofind.view.adapter.FilmAdapter
import com.example.kinofind.viewmodel.MainFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var binding: FragmentMainBinding

    private lateinit var rvFilms: RecyclerView
    private lateinit var filmAdapter: FilmAdapter
    private var filmList = LinkedList<Film>()

    private lateinit var viewModel: MainFragmentViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupRvFilms()

        viewModel = ViewModelProvider(this).get(MainFragmentViewModel::class.java)
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })

        viewModel.getData()
    }

    @Suppress("NAME_SHADOWING")
    private fun setupRvFilms() {
        rvFilms = binding.rvFilms
        rvFilms.setHasFixedSize(true)
        rvFilms.layoutManager = LinearLayoutManager(context)

        filmAdapter = FilmAdapter(filmList, object : OnItemViewClickListener {
            override fun onItemViewClick(film: Film) {
                val manager = activity?.supportFragmentManager
                manager?.let { manager ->
                    val bundle = Bundle().apply {
                        putParcelable(DetailsFragment.BUNDLE_EXTRA, film)
                    }
                    manager.beginTransaction()
                            .replace(R.id.container, DetailsFragment.newInstance(bundle))
                            .addToBackStack("")
                            .commitAllowingStateLoss()
                }
            }
        })
        rvFilms.adapter = filmAdapter
    }

    private fun renderData(appState: AppState?) {
        when (appState) {
            is AppState.Success -> {
                val filmData = appState.filmData
                setData(filmData)
            }
            is AppState.Loading -> {}
            is AppState.Error -> {
                Snackbar
                        .make(binding.root, getString(R.string.Error), Snackbar.LENGTH_INDEFINITE)
                        .show()
            }
        }
    }

    private fun setData(filmData: List<Film>) {
        filmList.clear()
        filmList.addAll(filmData)
        filmAdapter.notifyDataSetChanged()
    }
}