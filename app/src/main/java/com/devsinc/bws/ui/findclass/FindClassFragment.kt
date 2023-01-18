package com.devsinc.bws.ui.findclass

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.devsinc.bws.MainActivity
import com.devsinc.bws.R
import com.devsinc.bws.databinding.FragmentFindClassBinding
import com.devsinc.bws.model.DropdownListItem
import com.devsinc.bws.repository.Resource
import com.devsinc.bws.ui.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FindClassFragment : BindingFragment<FragmentFindClassBinding>() {

    private lateinit var activityList: List<DropdownListItem>

    companion object {
        fun newInstance() = FindClassFragment()
    }

    private val viewModel: FindClassViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentFindClassBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Find Class"
        viewModel.getClassSearchResult("0", "0", null)
        viewModel.getClassActivityList()

        binding.btnSearch.setOnClickListener {
            val activityId = activityList.find { it.name == binding.tieSport.text.toString() }?.id
            var location = binding.tieLocation.text.toString().split(",")
            if (location.size < 2) {
                location = listOf("0", "0")
            }
            viewModel.getClassSearchResult(location[0], location[1], activityId)
        }

        lifecycleScope.launchWhenStarted {
            viewModel.classSearchFlow.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val classAdapter = RecyclerAdapterClasses(resource.result)
                        binding.rvFindClass.adapter = classAdapter
                    }
                    is Resource.Error -> {
                        Log.d("TAG", "onViewCreated: ${resource.exception.message}")
                    }
                    else -> {

                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.classActivityListFlow.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        activityList = resource.result
                        val activityAdapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, resource.result.map { it.name })
                        binding.tieSport.setAdapter(activityAdapter)
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