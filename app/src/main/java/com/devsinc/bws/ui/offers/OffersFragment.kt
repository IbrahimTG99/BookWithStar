package com.devsinc.bws.ui.offers

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
import com.devsinc.bws.databinding.FragmentOffersBinding
import com.devsinc.bws.repository.Resource
import com.devsinc.bws.ui.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OffersFragment : BindingFragment<FragmentOffersBinding>() {

    companion object {
        fun newInstance() = OffersFragment()
    }

    private val viewModel: OffersViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentOffersBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Offers"
        viewModel.getOffers()
        lifecycleScope.launchWhenStarted {
            viewModel.offersFlow.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val offersAdapter = RecyclerAdapterOffers(resource.result)
                        binding.rvOffers.adapter = offersAdapter
                    }
                    is Resource.Error -> {
                        Log.d("TAG", "onViewCreated: ${resource.exception.message}")
                    }
                    else -> {}
                }
            }
        }
    }
}