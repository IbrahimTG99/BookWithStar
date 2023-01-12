package com.devsinc.bws.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.devsinc.bws.R
import com.devsinc.bws.databinding.FragmentHomeBinding
import com.devsinc.bws.databinding.FragmentSignInBinding
import com.devsinc.bws.repository.Resource
import com.devsinc.bws.ui.BindingFragment
import com.devsinc.bws.viewmodel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BindingFragment<FragmentHomeBinding>() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentHomeBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getHomeScreen()

        lifecycleScope.launchWhenStarted {
            viewModel.homeDataFlow.collect { resource ->
                when (resource) {
                    is Resource.Success -> {

                        val featuredVenueAdapter = RecyclerAdapterFeaturedVenues(resource.result.stadium)
                        binding.rvFeaturedVenues.adapter = featuredVenueAdapter
                        featuredVenueAdapter.setOnItemClickListener(object : RecyclerAdapterFeaturedVenues.OnItemClickListener {
                            override fun onItemClick(position: Int) {
                            }
                        })
                        val featuredClassAdapter = RecyclerAdapterFeaturedClasses(resource.result.featured_class)
                        binding.rvFeaturedClasses.adapter = featuredClassAdapter
                        featuredClassAdapter.setOnItemClickListener(object : RecyclerAdapterFeaturedClasses.OnItemClickListener {
                            override fun onItemClick(position: Int) {
                            }
                        })
                    }
                    is Resource.Error -> {
                        Log.w("HomeFragment", "onViewCreated: ${resource.exception}")
                    }
                    is Resource.Loading -> {

                    }
                    else -> {}
                }
            }
        }
    }
}