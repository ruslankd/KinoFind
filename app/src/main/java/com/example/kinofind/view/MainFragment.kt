package com.example.kinofind.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kinofind.*
import com.example.kinofind.databinding.FragmentMainBinding
import com.example.kinofind.model.AppState
import com.example.kinofind.model.OnItemViewClickListener
import com.example.kinofind.model.entities.Film
import com.example.kinofind.view.adapter.FilmAdapter
import com.example.kinofind.view.services.FilmLoadService
import com.example.kinofind.viewmodel.MainFragmentViewModel
import java.util.*

class MainFragment : Fragment() {

    companion object {
        const val BUNDLE_EXTRA = "film"

        fun newInstance() = MainFragment()
    }

    private lateinit var binding: FragmentMainBinding

    private lateinit var rvFilms: RecyclerView
    private lateinit var filmAdapter: FilmAdapter
    private var filmList = ArrayList<Film>()

    private val viewModel: MainFragmentViewModel by lazy {
        ViewModelProvider(this).get(MainFragmentViewModel::class.java)
    }

    private val loadResultsReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.getStringExtra(DETAILS_LOAD_RESULT_EXTRA)) {
                DETAILS_INTENT_EMPTY_EXTRA -> viewModel.setData(AppState.Error(Exception(DETAILS_INTENT_EMPTY_EXTRA)))
                DETAILS_DATA_EMPTY_EXTRA ->  viewModel.setData(AppState.Error(Exception(DETAILS_DATA_EMPTY_EXTRA)))
                DETAILS_RESPONSE_EMPTY_EXTRA ->  viewModel.setData(AppState.Error(Exception(DETAILS_RESPONSE_EMPTY_EXTRA)))
                DETAILS_REQUEST_ERROR_EXTRA ->  viewModel.setData(AppState.Error(Exception(DETAILS_REQUEST_ERROR_EXTRA)))
                DETAILS_REQUEST_ERROR_MESSAGE_EXTRA ->  viewModel.setData(AppState.Error(Exception(DETAILS_REQUEST_ERROR_MESSAGE_EXTRA)))
                DETAILS_URL_MALFORMED_EXTRA ->  viewModel.setData(AppState.Error(Exception(DETAILS_URL_MALFORMED_EXTRA)))
                DETAILS_RESPONSE_SUCCESS_EXTRA -> viewModel.setData(AppState.Success(
                        intent.getParcelableArrayListExtra(DETAILS_FILMS_EXTRA))
                )
                else -> viewModel.setData(AppState.Error(Exception("Unknown")))
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupRvFilms()

        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })

        val intent = Intent(requireContext(), FilmLoadService::class.java)
        FilmLoadService.start(requireContext(), intent)
    }


    @Suppress("NAME_SHADOWING")
    private fun setupRvFilms() {

        filmAdapter = FilmAdapter(filmList, object : OnItemViewClickListener {
            override fun onItemViewClick(film: Film) {
                val manager = activity?.supportFragmentManager
                manager?.let { manager ->
                    val bundle = Bundle().apply {
                        putParcelable(BUNDLE_EXTRA, film)
                    }
                    manager.beginTransaction()
                            .replace(R.id.container, DetailsFragment.newInstance(bundle))
                            .addToBackStack("")
                            .commitAllowingStateLoss()
                }
            }
        })

        rvFilms = binding.rvFilms.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = filmAdapter
        }
    }

    private fun renderData(appState: AppState?) {
        when (appState) {
            is AppState.Success -> {
                setData(appState.filmData)
            }
            is AppState.Loading -> {}
            is AppState.Error -> {
                rvFilms.showSnackBar(
                        appState.error?.message,
                        "Reload",
                        { viewModel.getData() }
                )
            }
        }
    }

    private fun setData(filmData: List<Film>) {
        filmList.clear()
        filmList.addAll(filmData)
        filmAdapter.notifyDataSetChanged()
    }

    override fun onStart() {
        super.onStart()
        LocalBroadcastManager.getInstance(requireContext())
                .registerReceiver(loadResultsReceiver, IntentFilter(DETAILS_INTENT_FILTER))
    }

    override fun onStop() {
        LocalBroadcastManager.getInstance(requireContext())
                .unregisterReceiver(loadResultsReceiver)
        super.onStop()
    }

}