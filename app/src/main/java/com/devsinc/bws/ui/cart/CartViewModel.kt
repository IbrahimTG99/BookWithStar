package com.devsinc.bws.ui.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsinc.bws.model.OmniString
import com.devsinc.bws.model.TimeSlot
import com.devsinc.bws.model.TimeSlotCart
import com.devsinc.bws.repository.CustomerRepository
import com.devsinc.bws.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: CustomerRepository
) : ViewModel() {
    private val _cartFlow = MutableStateFlow<Resource<List<TimeSlotCart>>?>(null)
    val cartFlow = _cartFlow.asStateFlow()

    private val _cartStatusFlow = MutableStateFlow<Resource<OmniString?>?>(null)
    val cartStatusFlow = _cartStatusFlow.asStateFlow()

    fun getCart(slotIds: String, customerId: Int) = viewModelScope.launch {
        _cartFlow.value = Resource.Loading
        _cartFlow.value = repository.getTimeSlotReservation(slotIds, customerId)
    }

    fun changeCartStatus(slotIds: String, status: Int) = viewModelScope.launch {
        _cartStatusFlow.value = Resource.Loading
        _cartStatusFlow.value = repository.changeCartStatus(slotIds, status)
    }
}