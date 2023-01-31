package com.devsinc.bws.ui.findteammates

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
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.devsinc.bws.MainActivity
import com.devsinc.bws.R
import com.devsinc.bws.databinding.FragmentFindTeammatesBinding
import com.devsinc.bws.model.FindPlayerParams
import com.devsinc.bws.repository.Resource
import com.devsinc.bws.ui.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindTeammatesFragment : BindingFragment<FragmentFindTeammatesBinding>() {

    companion object {
        fun newInstance() = FindTeammatesFragment()
    }

    private val viewModel: FindTeammatesViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentFindTeammatesBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Find Teammates"

        viewModel.findPlayer(FindPlayerParams(1, 1, 1, 1, 1))

        binding.fabFilter.setOnClickListener {
            val bottomSheetFragment = BottomSheetFragment(viewModel)
            bottomSheetFragment.show(parentFragmentManager, BottomSheetFragment.TAG)
        }

        binding.playerFinderBtn.setOnClickListener {
            findNavController().navigate(R.id.formPlayerFinderFragment)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.findTeammatesFlow.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        viewModel.genderList = resource.result[0].gender_list
                        viewModel.availabilityList = resource.result[0].avaibility_list
                        viewModel.skillList = resource.result[0].skill_list
                        viewModel.venueList = resource.result[0].venue_list
                        viewModel.sportList = resource.result[0].sports_list

                        val playerAdapter = RecyclerAdapterPlayers(resource.result)
                        binding.recyclerView.adapter = playerAdapter
                    }
                    is Resource.Error -> {
                        Log.d("TAG", "onViewCreated: ${resource.exception.message}")
                    }
                    is Resource.Loading -> {
                        Log.d("TAG", "onViewCreated: Loading")
                    }
                    else -> {}
                }
            }
        }

    }
}