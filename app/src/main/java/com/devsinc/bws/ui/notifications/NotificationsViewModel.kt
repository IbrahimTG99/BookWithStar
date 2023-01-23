package com.devsinc.bws.ui.notifications

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsinc.bws.repository.CustomerRepository
import com.devsinc.bws.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val repository: CustomerRepository
) : ViewModel() {
    private val _notificationsFlow = MutableStateFlow<Resource<String>?>(null)
    val notificationsFlow = _notificationsFlow.asStateFlow()

    fun getNotifications() = viewModelScope.launch {
        _notificationsFlow.value = Resource.Loading
        _notificationsFlow.value = repository.getPushNotificationData()
    }
}