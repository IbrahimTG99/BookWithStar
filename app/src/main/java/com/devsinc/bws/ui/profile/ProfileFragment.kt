package com.devsinc.bws.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.devsinc.bws.R
import com.devsinc.bws.databinding.FragmentProfileBinding
import com.devsinc.bws.repository.Resource
import com.devsinc.bws.ui.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : BindingFragment<FragmentProfileBinding>() {

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private val viewModel: ProfileViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentProfileBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCustomerDetails()

        lifecycleScope.launchWhenStarted {
            viewModel.customerFlow.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        binding.etFirstName.setText(resource.result.first_name)
                        binding.etLastName.setText(resource.result.last_name)
                        binding.etEmail.setText(resource.result.cus_email)
                        binding.etMobileNumber.setText(resource.result.cus_mobile)
                        binding.etDisplayName.setText(resource.result.cus_name)
                        binding.tvWelcomeUser.text = getString(R.string.welcome_placeholder, resource.result.cus_name)
                        Glide.with(requireContext()).load(resource.result.cus_photo).into(binding.ivProfileImage)
                        val adapter = binding.spCountry.adapter
                        for (i in 0 until adapter.count) {
                            if (adapter.getItem(i).toString() == resource.result.cus_country) {
                                binding.spCountry.setSelection(i)
                                break
                            }
                        }
                    }
                    is Resource.Error -> {
                        Log.w("ProfileFragment", "onViewCreated: ${resource.exception}")
                    }
                    is Resource.Loading -> {

                    }
                    else -> {}
                }
            }
        }
    }
}