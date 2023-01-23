package com.devsinc.bws.ui.notifications

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
import com.devsinc.bws.R
import com.devsinc.bws.databinding.FragmentNotificationsBinding
import com.devsinc.bws.repository.Resource
import com.devsinc.bws.ui.BindingFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationsFragment : BindingFragment<FragmentNotificationsBinding>() {

    companion object {
        fun newInstance() = NotificationsFragment()
    }

    private val viewModel: NotificationsViewModel by viewModels()

    override val bindingInflater: (LayoutInflater) -> ViewBinding
        get() = FragmentNotificationsBinding::inflate

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getNotifications()
        (activity as AppCompatActivity).supportActionBar?.title = "Notifications"
        lifecycleScope.launchWhenStarted {
            viewModel.notificationsFlow.collect { resource ->
                when (resource) {
                    is Resource.Success -> {
                        binding.textNotifications.text = resource.result
                    }
                    is Resource.Error -> {
                        binding.textNotifications.text = resource.exception.message
                    }
                    else -> {}
                }
            }
        }
    }
}