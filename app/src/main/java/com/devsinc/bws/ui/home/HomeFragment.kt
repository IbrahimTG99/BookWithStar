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
import com.devsinc.bws.ui.stadiumdetails.StadiumDetailsFragment
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
        viewModel.getCustomer()

        lifecycleScope.launchWhenStarted {
            viewModel.customerFlow.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        (activity as MainActivity).binding.navView.getHeaderView(0)
                            .findViewById<TextView>(R.id.tv_user_name).text = "%s %s".format(
                            resource.result.first_name, resource.result.last_name
                        )
                        (activity as MainActivity).supportActionBar?.title =
                            "Hey, %s!".format(resource.result.first_name)
                    }
                    is Resource.Error -> {
                        Log.d("HomeFragment", resource.exception.message.toString())
                    }
                    is Resource.Loading -> {
                        Log.d("HomeFragment", "Loading")
                    }
                    else -> {}
                }
            }
        }

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
                                findNavController().navigate(
                                    R.id.stadiumDetailsFragment,
                                    Bundle().apply {
                                        putInt(
                                            "venue_id",
                                            resource.result.stadium[position].venue_id
                                        )
                                    })
                            }
                        })
                        val featuredClassAdapter =
                            RecyclerAdapterFeaturedClasses(resource.result.featured_class)
                        binding.rvFeaturedClasses.adapter = featuredClassAdapter
                        featuredClassAdapter.setOnItemClickListener(object :
                            RecyclerAdapterFeaturedClasses.OnItemClickListener {
                            override fun onItemClick(position: Int) {
                                findNavController().navigate(
                                    R.id.classDetailsFragment,
                                    Bundle().apply {
                                        putInt(
                                            "class_id",
                                            resource.result.featured_class[position].cid
                                        )
                                    })
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