package com.example.kinofind.view

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.kinofind.IMAGE_PATH
import com.example.kinofind.R
import com.example.kinofind.databinding.FragmentDetailsBinding
import com.example.kinofind.model.database.HistoryEntity
import com.example.kinofind.model.entities.Film
import com.example.kinofind.viewmodel.DetailsViewModel
import com.example.kinofind.viewmodel.MainFragmentViewModel

class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private var film: Film? = null
    private var liked: Boolean = false

    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        film = arguments?.getParcelable(MainFragment.BUNDLE_EXTRA)
        film?.let { with(binding) {
                tvDetailTitle.text = it.title
                tvDetailRatingAndYear.text = "Release date: ${it.release_date}\nRating: ${it.vote_average}"
                tvDetailDescription.text = it.overview
                val uri: Uri = Uri.parse("$IMAGE_PATH${it.poster_path}")
                ivDetailFilmImage.setImageURI(uri)
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupLikeBtn()
        setupSaveBtn()
        viewModel.getLiveData().observe(viewLifecycleOwner, { renderData(it) })
        film?.let {
            viewModel.getData(it.title)
        }
    }

    private fun setupLikeBtn() = with(binding) {
        btnLike.setOnClickListener {
            setLikeBtn(liked)
        }
    }

    private fun setLikeBtn(l: Boolean) = with(binding) {
        liked = if (l) {
            btnLike.setImageResource(R.drawable.ic_favorite_border_24)
            false
        } else {
            btnLike.setImageResource(R.drawable.ic_baseline_favorite_24)
            true
        }
    }

    private fun renderData(entity: HistoryEntity?) {
        entity?.let {
            binding.note.setText(it.note)
            setLikeBtn(!entity.like)
        }
    }

    private fun setupSaveBtn() = with(binding) {
        btnSave.setOnClickListener {
            film?.let {
                viewModel.setData(it, binding.note.text.toString(), liked)
            }
        }
    }



    companion object {

        fun newInstance(bundle: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

}