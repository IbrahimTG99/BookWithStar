package com.devsinc.bws.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    init {
        if (repository.customer == null) {
            viewModelScope.launch {
                repository.getCustomer()
            }
        }
    }

    fun getHomeScreen() = viewModelScope.launch {
        _homeDataFlow.value = Resource.Loading
        _homeDataFlow.value = repository.getHomeScreen()
    }
}