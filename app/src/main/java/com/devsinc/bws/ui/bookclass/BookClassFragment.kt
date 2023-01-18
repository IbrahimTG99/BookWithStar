package com.devsinc.bws.ui.bookclass

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
import com.devsinc.bws.MainActivity
import com.devsinc.bws.R
import com.devsinc.bws.databinding.FragmentBookClassBinding
import com.devsinc.bws.repository.Resource
import com.devsinc.bws.ui.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BookClassFragment : BindingFragment<FragmentBookClassBinding>() {

    companion object {
        fun newInstance() = BookClassFragment()
    }

    private val viewModel: BookClassViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentBookClassBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Book Class"
        viewModel.getBookClass()
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