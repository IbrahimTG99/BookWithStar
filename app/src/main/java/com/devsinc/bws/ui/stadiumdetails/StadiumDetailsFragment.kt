package com.devsinc.bws.ui.stadiumdetails

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.devsinc.bws.R
import com.devsinc.bws.databinding.FragmentStadiumDetailsBinding
import com.devsinc.bws.model.DetailsData
import com.devsinc.bws.repository.Resource
import com.devsinc.bws.ui.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class StadiumDetailsFragment : BindingFragment<FragmentStadiumDetailsBinding>() {

    companion object {
        fun newInstance() = StadiumDetailsFragment()
    }

    private val viewModel: StadiumDetailsViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentStadiumDetailsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Stadium Details"

        viewModel.getStadiumDetails(arguments?.getInt("venue_id") ?: 0)

        lifecycleScope.launchWhenStarted {
            viewModel.stadiumDetailsFlow.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        Glide.with(requireContext())
                            .load(resource.result.venue_slider[0])
                            .into(binding.image)
                        binding.tvTitle.text = resource.result.venue_name
                        binding.tvDescription.text = resource.result.venue_detail
                        for (i in resource.result.venue_icon) {
                            val imageView = ImageView(requireContext())
                            imageView.layoutParams = ViewGroup.LayoutParams(100, 100)
                            Glide.with(requireContext())
                                .load(i)
                                .into(imageView)
                            binding.linearIcons.addView(imageView)
                        }
                        val detailsList = mutableListOf<DetailsData>()
                        detailsList.add(DetailsData.Heading("Address"))
                        detailsList.add(
                            DetailsData.Info(
                                resource.result.venue_address,
                                R.drawable.ic_baseline_location_on_24.toString(),
                                false
                            )
                        )
                        detailsList.add(
                            DetailsData.Info(
                                resource.result.venue_email,
                                R.drawable.baseline_email_24.toString(),
                                false
                            )
                        )
                        detailsList.add(
                            DetailsData.Info(
                                resource.result.venue_phone,
                                R.drawable.ic_baseline_call_24.toString(),
                                false
                            )
                        )
                        detailsList.add(DetailsData.Heading("Timing"))
                        detailsList.add(
                            DetailsData.Info(
                                resource.result.venue_timing,
                                R.drawable.ic_time.toString(),
                                false
                            )
                        )
                        detailsList.add(DetailsData.Heading("Amenities"))
                        for (i in resource.result.venue_ammenities) {
                            detailsList.add(DetailsData.Info(i.name, i.icon, true))
                        }

                        binding.rvDetails.adapter = RecyclerAdapterStadiumDetails(detailsList)
                    }
                    is Resource.Error -> {
                        Log.w("StadiumDetails", "onViewCreated: ${resource.exception}")
                    }
                    is Resource.Loading -> {

                    }
                    else -> {}
                }
            }
        }
    }
}
