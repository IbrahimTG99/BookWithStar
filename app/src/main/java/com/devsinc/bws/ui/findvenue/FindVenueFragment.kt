package com.devsinc.bws.ui.findvenue

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.devsinc.bws.MainActivity
import com.devsinc.bws.R
import com.devsinc.bws.databinding.FragmentFindVenueBinding
import com.devsinc.bws.model.DropdownListItem
import com.devsinc.bws.repository.Resource
import com.devsinc.bws.ui.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindVenueFragment : BindingFragment<FragmentFindVenueBinding>() {

    private lateinit var sportList: List<DropdownListItem>

    companion object {
        fun newInstance() = FindVenueFragment()
    }

    private val viewModel: FindVenueViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentFindVenueBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Find Venue"
        viewModel.getVenueByLocation("0", "0", null)
        viewModel.getSportList()

        binding.btnSearch.setOnClickListener {
            val sportId = sportList.find { it.name == binding.tieSport.text.toString() }?.id
            var location = binding.tieLocation.text.toString().split(",")
            if (location.size < 2) {
                location = listOf("0", "0")
            }
            viewModel.getVenueByLocation(location[0], location[1], sportId)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.venueByLocationFlow.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val venueAdapter = RecyclerAdapterVenues(resource.result)
                        binding.rvVenue.adapter = venueAdapter
                    }
                    is Resource.Error -> {
                        Log.d("TAG", "onViewCreated: ${resource.exception.message}")
                    }
                    is Resource.Loading -> {
                        Log.d("TAG", "onViewCreated: Loading")
                    }
                    else -> {
                        // Do nothing
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.sportListFlow.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        sportList = resource.result
                        val sportAdapter = ArrayAdapter(
                            requireContext(),
                            R.layout.dropdown_item,
                            sportList.map { it.name }
                        )
                        binding.tieSport.setAdapter(sportAdapter)
                    }
                    is Resource.Error -> {
                        Log.d("TAG", "onViewCreated: ${resource.exception.message}")
                    }
                    is Resource.Loading -> {
                        Log.d("TAG", "onViewCreated: Loading")
                    }
                    else -> {
                        // Do nothing
                    }
                }
            }
        }
    }
}