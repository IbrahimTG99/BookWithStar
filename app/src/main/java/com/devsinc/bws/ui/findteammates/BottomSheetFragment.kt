package com.devsinc.bws.ui.findteammates

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import com.devsinc.bws.R
import com.devsinc.bws.databinding.BottomSheetFilterFindTeammatesBinding
import com.devsinc.bws.model.FindPlayerParams
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.WithFragmentBindings

@AndroidEntryPoint
class BottomSheetFragment(private val viewModel: FindTeammatesViewModel) :
    BottomSheetDialogFragment() {
    private lateinit var binding: BottomSheetFilterFindTeammatesBinding

    companion object {
        const val TAG = "ModalBottomSheet"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.bottom_sheet_filter_find_teammates, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = BottomSheetFilterFindTeammatesBinding.bind(view)

        binding.ivClose.setOnClickListener {
            dismiss()
        }

        val genderAdapter =
            ArrayAdapter(
                requireContext(),
                R.layout.dropdown_item,
                viewModel.genderList.map { it.name })
        val sportAdapter =
            ArrayAdapter(
                requireContext(),
                R.layout.dropdown_item,
                viewModel.sportList.map { it.name })
        val venueAdapter =
            ArrayAdapter(
                requireContext(),
                R.layout.dropdown_item,
                viewModel.venueList.map { it.name })
        val skillAdapter =
            ArrayAdapter(
                requireContext(),
                R.layout.dropdown_item,
                viewModel.skillList.map { it.name })
        val availabilityAdapter =
            ArrayAdapter(
                requireContext(),
                R.layout.dropdown_item,
                viewModel.availabilityList.map { it.name })

        binding.tieSelectAvailability.setAdapter(availabilityAdapter)
        binding.tieSelectGender.setAdapter(genderAdapter)
        binding.tieSelectSkills.setAdapter(skillAdapter)
        binding.tieSelectSports.setAdapter(sportAdapter)
        binding.tieSelectVenue.setAdapter(venueAdapter)

        binding.FilterBtn.setOnClickListener {
            val sport =
                viewModel.sportList.find { it.name == binding.tieSelectSports.text.toString() }?.id
                    ?: 1
            val venue =
                viewModel.venueList.find { it.name == binding.tieSelectVenue.text.toString() }?.id
                    ?: 1
            val availability =
                viewModel.availabilityList.find { it.name == binding.tieSelectAvailability.text.toString() }?.id
                    ?: 1
            val skill =
                viewModel.skillList.find { it.name == binding.tieSelectSkills.text.toString() }?.id
                    ?: 1
            val gender =
                viewModel.genderList.find { it.name == binding.tieSelectGender.text.toString() }?.id
                    ?: 1

            viewModel.findPlayer(
                FindPlayerParams(
                    sport,
                    gender,
                    availability,
                    skill,
                    venue
                )
            )
            dismiss()
        }
    }
}