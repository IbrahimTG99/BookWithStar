package com.devsinc.bws.ui.bookclass

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
import com.devsinc.bws.databinding.DropdownItemBinding
import com.devsinc.bws.databinding.FragmentBookClassBinding
import com.devsinc.bws.model.DropdownListItem
import com.devsinc.bws.repository.Resource
import com.devsinc.bws.ui.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookClassFragment : BindingFragment<FragmentBookClassBinding>() {

    companion object {
        fun newInstance() = BookClassFragment()
    }

    private val viewModel: BookClassViewModel by viewModels()
    private lateinit var sportList: List<DropdownListItem>
    private lateinit var sportTypeList: List<DropdownListItem>

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentBookClassBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Book Class"
        viewModel.getSportList()

        lifecycleScope.launchWhenStarted {
            viewModel.sportListFlow.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        sportList = resource.result
                        binding.tieSport.setAdapter(
                            ArrayAdapter(
                                requireContext(),
                                R.layout.dropdown_item,
                                sportList.map { it.name }
                            )
                        )
                        binding.tieSport.setText(sportList[0].name, false)
                        viewModel.getSportTypeList(sportList[0].id)
                        binding.tieSport.setOnItemClickListener { _, _, position, _ ->
                            viewModel.getSportTypeList(sportList[position].id)
                        }
                    }
                    is Resource.Error -> {
                        Log.d("TAG", "onViewCreated: ${resource.exception.message}")
                    }
                    else -> {}
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.sportTypeListFlow.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        sportTypeList = resource.result
                        binding.tieSportType.setAdapter(
                            ArrayAdapter(
                                requireContext(),
                                R.layout.dropdown_item,
                                sportTypeList.map { it.name }
                            )
                        )
                        binding.tieSportType.setText(sportTypeList[0].name, false)
                        viewModel.getBookClass(sportTypeList[0].id)
                        binding.tieSportType.setOnItemClickListener { _, _, position, _ ->
                            viewModel.getBookClass(sportTypeList[position].id)
                        }
                    }
                    is Resource.Error -> {
                        Log.d("TAG", "onViewCreated: ${resource.exception.message}")
                    }
                    else -> {}
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.bookClassFlow.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val bookClassAdapter = RecyclerAdapterBookClasses(resource.result)
                        binding.rvBookClass.adapter = bookClassAdapter
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