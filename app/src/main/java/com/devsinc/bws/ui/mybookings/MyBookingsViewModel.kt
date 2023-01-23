package com.devsinc.bws.ui.mybookings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsinc.bws.model.BookingItem
import com.devsinc.bws.repository.CustomerRepository
import com.devsinc.bws.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyBookingsViewModel @Inject constructor(
    private val repository: CustomerRepository
) : ViewModel() {
    private val _myBookingsFlow = MutableStateFlow<Resource<List<BookingItem>>?>(null)
    val myBookingsFlow = _myBookingsFlow.asStateFlow()

    fun getMyBookings() = viewModelScope.launch {
        _myBookingsFlow.value = Resource.Loading
        _myBookingsFlow.value = repository.getMyBookings()
    }
}