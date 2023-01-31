package com.devsinc.bws.ui.classpackage

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
import com.devsinc.bws.databinding.FragmentClassPackageBinding
import com.devsinc.bws.repository.Resource
import com.devsinc.bws.ui.BindingFragment
import com.devsinc.bws.ui.bookclass.RecyclerAdapterBookClasses
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClassPackageFragment : BindingFragment<FragmentClassPackageBinding>() {

    companion object {
        fun newInstance() = ClassPackageFragment()
    }

    private val viewModel: ClassPackageViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentClassPackageBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar?.title = "Class Package"
        viewModel.getClassPackage(arguments?.getInt("classId") ?: 0)
        binding.tvClassName.text = arguments?.getString("className") ?: "Class Name"

        lifecycleScope.launchWhenStarted {
            viewModel.classPackageFlow.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        binding.rvBookClass.adapter = RecyclerAdapterBookClasses(resource.result)
                    }
                    is Resource.Error -> {
                        Log.e("ClassPackageFragment", resource.exception.message.toString())
                    }
                    is Resource.Loading -> {
                        Log.d("ClassPackageFragment", "Loading")
                    }
                    else -> {}
                }
            }
        }
    }
}
