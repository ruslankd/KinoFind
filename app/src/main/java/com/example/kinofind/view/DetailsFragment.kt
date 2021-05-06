package com.example.kinofind.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.kinofind.R
import com.example.kinofind.databinding.FragmentDetailsBinding
import com.example.kinofind.model.entities.Film

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val film = arguments?.getParcelable<Film>(BUNDLE_EXTRA)
        film?.let {
            binding.tvDetailTitle.text = it.name
            binding.tvDetailRatingAndYear.text = "Year: ${it.year}\nRating: ${it.rating}"
            binding.tvDetailDescription.text = it.description
            binding.ivDetailFilmImage.setImageResource(R.drawable.ic_launcher_foreground)
        }
    }

    companion object {
        const val BUNDLE_EXTRA = "film"

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}