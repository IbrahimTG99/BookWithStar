package com.devsinc.bws.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsinc.bws.model.Customer
import com.devsinc.bws.model.CustomerDetails
import com.devsinc.bws.repository.CustomerRepository
import com.devsinc.bws.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: CustomerRepository
) : ViewModel() {
    private val _customerFlow = MutableStateFlow<Resource<CustomerDetails>?>(null)
    val customerFlow = _customerFlow

    fun getCustomerDetails() = viewModelScope.launch {
        _customerFlow.value = Resource.Loading
        _customerFlow.value = repository.getCustomerDetails()
    }
}