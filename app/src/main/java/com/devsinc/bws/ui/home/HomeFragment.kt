package com.devsinc.bws.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.devsinc.bws.MainActivity
import com.devsinc.bws.R
import com.devsinc.bws.databinding.FragmentHomeBinding
import com.devsinc.bws.repository.Resource
import com.devsinc.bws.ui.BindingFragment
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
        (activity as MainActivity).supportActionBar?.title = "Hey, %s!".format((activity as MainActivity).customer.first_name)

        viewModel.getHomeScreen()

        binding.cardBookVenue.setOnClickListener {
            findNavController().navigate(R.id.bookOnlineFragment)
        }
        binding.cardBookClass.setOnClickListener {
            findNavController().navigate(R.id.bookClassFragment)
        }
        binding.cardActivateProfile.setOnClickListener {
            findNavController().navigate(R.id.findTeammatesFragment)
        }
        binding.cardAbout.setOnClickListener {
            findNavController().navigate(R.id.aboutFragment)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.homeDataFlow.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        (activity as MainActivity).binding.navView.getHeaderView(0)
                            .findViewById<TextView>(R.id.tv_user_points).text =
                            resource.result.total_reward_point.toString()

                        val featuredVenueAdapter =
                            RecyclerAdapterFeaturedVenues(resource.result.stadium)
                        binding.rvFeaturedVenues.adapter = featuredVenueAdapter
                        featuredVenueAdapter.setOnItemClickListener(object :
                            RecyclerAdapterFeaturedVenues.OnItemClickListener {
                            override fun onItemClick(position: Int) {
                            }
                        })
                        val featuredClassAdapter =
                            RecyclerAdapterFeaturedClasses(resource.result.featured_class)
                        binding.rvFeaturedClasses.adapter = featuredClassAdapter
                        featuredClassAdapter.setOnItemClickListener(object :
                            RecyclerAdapterFeaturedClasses.OnItemClickListener {
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