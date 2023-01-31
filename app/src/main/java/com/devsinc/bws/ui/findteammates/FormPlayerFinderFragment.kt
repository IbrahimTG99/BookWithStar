package com.devsinc.bws.ui.findteammates

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.devsinc.bws.MainActivity
import com.devsinc.bws.R
import com.devsinc.bws.databinding.FormFindTeammatesPlayerInfoBinding
import com.devsinc.bws.repository.Resource
import com.devsinc.bws.ui.BindingFragment
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class FormPlayerFinderFragment : BindingFragment<FormFindTeammatesPlayerInfoBinding>() {

    companion object {
        fun newInstance() = FormPlayerFinderFragment()
    }

    private val viewModel: FindTeammatesViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FormFindTeammatesPlayerInfoBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Player Finder"

        viewModel.getPlayerFinderData()

        lifecycleScope.launchWhenStarted {
            viewModel.playerFinderFLow.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        resource.result.player_interest.forEach {
                            val chip = Chip(requireContext())
                            chip.text = it.name
                            chip.isCheckable = true
                            chip.chipBackgroundColor = resources.getColorStateList(R.color.accent_yellow, null)
                            binding.chipGroup.addView(chip)
                        }

                        val teamType = ArrayAdapter(requireContext(), R.layout.dropdown_item, resource.result.player_type.map { it.name })
                        binding.tieIndividualOrTeam.setAdapter(teamType)
                        val availability = ArrayAdapter(requireContext(), R.layout.dropdown_item, resource.result.avaibility.map { it.name })
                        binding.tieAvailability.setAdapter(availability)
                        val age = ArrayAdapter(requireContext(), R.layout.dropdown_item, resource.result.age_group_list.map { it.name })
                        binding.tieAge.setAdapter(age)
                        val skill = ArrayAdapter(requireContext(), R.layout.dropdown_item, resource.result.skill_list.map { it.name })
                        binding.tieSkills.setAdapter(skill)
                        val gender = ArrayAdapter(requireContext(), R.layout.dropdown_item, resource.result.gender_list.map { it.name })
                        binding.tieGender.setAdapter(gender)
                        val venue = ArrayAdapter(requireContext(), R.layout.dropdown_item, resource.result.venue_list.map { it.name })
                        binding.tieVenue.setAdapter(venue)
                        val location = ArrayAdapter(requireContext(), R.layout.dropdown_item, resource.result.player_location.map { it.name })
                        binding.tieLocation.setAdapter(location)

                        binding.updateBtn.setOnClickListener {
                            Toast.makeText(requireContext(), "App Updated", Toast.LENGTH_SHORT).show()
                        }
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