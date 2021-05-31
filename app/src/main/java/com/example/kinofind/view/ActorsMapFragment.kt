package com.example.kinofind.view

import android.location.Address
import android.location.Geocoder
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.kinofind.R
import com.example.kinofind.databinding.FragmentActorsMapBinding
import com.example.kinofind.model.rest_entities.BaseActorByNameDTO
import com.example.kinofind.model.rest_entities.BaseActorDTO
import com.example.kinofind.model.rest_interaction.BackendRepo

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

class ActorsMapFragment : Fragment() {

    private lateinit var binding: FragmentActorsMapBinding
    private lateinit var map: GoogleMap

    private val mapCallback = OnMapReadyCallback { googleMap ->
        map = googleMap
        val sydney = LatLng(-34.0, 151.0)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    companion object {
        fun newInstance() = ActorsMapFragment()
    }

    private val callbackWithId = object : Callback<BaseActorByNameDTO> {
        override fun onResponse(call: Call<BaseActorByNameDTO>, response: Response<BaseActorByNameDTO>) {
            if(response.isSuccessful) {
                var id = 0L
                response.body()?.let {
                    id = it.results[0].id
                }
                BackendRepo.api.getActorById(id).enqueue(callbackWithBirthdayPlace)
            } else {
                binding.textAddress.text = getString(R.string.error)
            }
        }

        override fun onFailure(call: Call<BaseActorByNameDTO>, t: Throwable) {
            Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
        }
    }

    private val callbackWithBirthdayPlace = object : Callback<BaseActorDTO> {
        override fun onResponse(call: Call<BaseActorDTO>, response: Response<BaseActorDTO>) {
            if(response.isSuccessful) {
                var place = ""
                response.body()?.let {
                    place = it.placeOfBirth
                }
                binding.textAddress.text = place
                initSearchByAddress(place)
            } else {
                binding.textAddress.text = getString(R.string.error)
            }
        }

        override fun onFailure(call: Call<BaseActorDTO>, t: Throwable) {
            Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
        }

    }

    private fun initSearchByAddress(place: String) {
        val geoCoder = Geocoder(context)
        Thread {
            try {
                val addresses = geoCoder.getFromLocationName(place, 1)
                if (addresses.isNotEmpty()) {
                    goToAddress(addresses.first(), binding.root, place)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun goToAddress(address: Address, view: View, searchText: String) {
        val location = LatLng(address.latitude, address.longitude)
        view.post {
            setMarker(location, searchText)
            map.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                            location,
                            10f
                    )
            )
        }
    }

    private fun setMarker(location: LatLng, title: String) {
        map.clear()
        map.addMarker(
                MarkerOptions()
                        .position(location)
                        .title(title)
        )
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = FragmentActorsMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(mapCallback)

        setupSearchButton()
    }

    private fun setupSearchButton() = with(binding) {
        buttonSearch.setOnClickListener {
            getActorIdByName(searchActor.text.toString())
        }
    }

    private fun getActorIdByName(actorName: String) {
        val query = actorName.replace(' ', '+')
        BackendRepo.api.getActorByName(query = query).enqueue(callbackWithId)
    }
}