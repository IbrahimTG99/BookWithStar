package com.devsinc.bws.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsinc.bws.model.Customer
import com.devsinc.bws.model.CustomerDetails
import com.devsinc.bws.model.HomeScreenData
import com.devsinc.bws.repository.CustomerRepository
import com.devsinc.bws.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CustomerRepository
) : ViewModel() {
    private val _homeDataFlow = MutableStateFlow<Resource<HomeScreenData>?>(null)
    val homeDataFlow = _homeDataFlow.asStateFlow()

    private val _customerFlow = MutableStateFlow<Resource<CustomerDetails>?>(null)
    val customerFlow = _customerFlow.asStateFlow()

    fun getHomeScreen() = viewModelScope.launch {
        _homeDataFlow.value = Resource.Loading
        _homeDataFlow.value = repository.getHomeScreen()
    }

    fun getCustomer() = viewModelScope.launch {
        _customerFlow.value = Resource.Loading
        _customerFlow.value = repository.getCustomerDetails()
    }
}