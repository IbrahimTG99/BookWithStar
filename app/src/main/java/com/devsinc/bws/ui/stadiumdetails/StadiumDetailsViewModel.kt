package com.devsinc.bws.ui.stadiumdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsinc.bws.model.VenueDetail
import com.devsinc.bws.repository.CustomerRepository
import com.devsinc.bws.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StadiumDetailsViewModel @Inject constructor(
    private val repository: CustomerRepository
) : ViewModel() {
    private val _stadiumDetailsFlow = MutableStateFlow<Resource<VenueDetail>?>(null)
    val stadiumDetailsFlow = _stadiumDetailsFlow.asStateFlow()

    fun getStadiumDetails(venueId: Int) = viewModelScope.launch {
        _stadiumDetailsFlow.value = Resource.Loading
        _stadiumDetailsFlow.value = repository.getVenueDetail(venueId)
    }
}