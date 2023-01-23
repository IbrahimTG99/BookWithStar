package com.devsinc.bws.ui.classdetails

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
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
import com.devsinc.bws.databinding.FragmentClassDetailsBinding
import com.devsinc.bws.model.DetailsData
import com.devsinc.bws.repository.Resource
import com.devsinc.bws.ui.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClassDetailsFragment : BindingFragment<FragmentClassDetailsBinding>() {

    companion object {
        fun newInstance() = ClassDetailsFragment()
    }

    private val viewModel: ClassDetailsViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentClassDetailsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Class Details"
        Log.d("ClassDetailsFragment", arguments?.getInt("class_id").toString())
        viewModel.getClassDetails(arguments?.getInt("class_id") ?: 0)

        lifecycleScope.launchWhenStarted {
            viewModel.classDetailsFlow.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        Glide.with(requireContext())
                            .load(resource.result.class_slider[0])
                            .into(binding.image)
                        binding.tvTitle.text = resource.result.class_name
                        binding.tvDescription.text = resource.result.class_detail
                        for (i in resource.result.class_icon) {
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
                                resource.result.class_address,
                                R.drawable.ic_baseline_location_on_24.toString(),
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
                        if (resource.result.class_choose != null) {
                            detailsList.add(DetailsData.Heading("Why Choose Us"))
                            for (i in resource.result.class_choose) {
                                detailsList.add(
                                    DetailsData.Info(
                                        i,
                                        R.drawable.ic_done.toString(),
                                        false
                                    )
                                )
                            }
                        }
                        detailsList.add(DetailsData.Heading("Amenities"))
                        for (i in resource.result.class_ammenities) {
                            detailsList.add(
                                DetailsData.Info(
                                    i.name,
                                    i.icon,
                                    true
                                )
                            )
                        }

                        binding.rvDetails.adapter = RecyclerAdapterClassDetails(detailsList)
                        binding.rvSchedule.adapter =
                            RecyclerAdapterClassSchedule(resource.result.schedule)
                    }
                    is Resource.Error -> {
                        Log.w("ClassDetails", "onViewCreated: ${resource.exception}")
                    }
                    is Resource.Loading -> {

                    }
                    else -> {}
                }

            }
        }

    }

}