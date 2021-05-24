package com.example.kinofind.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.kinofind.R
import com.example.kinofind.databinding.FragmentMainBinding
import com.example.kinofind.databinding.SettingsFragmentBinding
import com.example.kinofind.viewmodel.SettingsViewModel

class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var binding: SettingsFragmentBinding

    private val viewModel: SettingsViewModel by lazy {
        ViewModelProvider(this).get(SettingsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        binding = SettingsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.title = "Settings"
        setupSwitch()

        viewModel.getLiveData().observe(viewLifecycleOwner, {
            binding.switch18.isChecked = it
        })
    }

    private fun setupSwitch() = with(binding) {
        switch18.setOnCheckedChangeListener { buttonView, isChecked ->
            viewModel.setData(isChecked)
        }
    }

    override fun onStart() {
        super.onStart()
        viewModel.getData()
    }

}