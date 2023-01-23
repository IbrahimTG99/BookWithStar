package com.devsinc.bws.ui.mybookings

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.devsinc.bws.R
import com.devsinc.bws.databinding.FragmentMyBookingsBinding
import com.devsinc.bws.repository.Resource
import com.devsinc.bws.ui.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyBookingsFragment : BindingFragment<FragmentMyBookingsBinding>() {

    companion object {
        fun newInstance() = MyBookingsFragment()
    }

    private val viewModel: MyBookingsViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentMyBookingsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "My Bookings"
        viewModel.getMyBookings()
        lifecycleScope.launchWhenStarted {
            viewModel.myBookingsFlow.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        binding.tvMyBookings.text = resource.result.toString()
                    }
                    is Resource.Error -> {
                        binding.tvMyBookings.text = resource.exception.message
                    }
                    else -> {}
                }
            }
        }
    }
}