package com.devsinc.bws.ui.findvenue

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsinc.bws.model.DropdownListItem
import com.devsinc.bws.model.Venue
import com.devsinc.bws.repository.CustomerRepository
import com.devsinc.bws.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FindVenueViewModel @Inject constructor(
    private val repository: CustomerRepository
) : ViewModel() {
    private val _venueByLocationFlow = MutableStateFlow<Resource<List<Venue>>?>(null)
    val venueByLocationFlow = _venueByLocationFlow.asStateFlow()

    private val _sportListFlow = MutableStateFlow<Resource<List<DropdownListItem>>?>(null)
    val sportListFlow = _sportListFlow.asStateFlow()

    fun getVenueByLocation(lat: String, lng: String, sport: Int?) = viewModelScope.launch {
        _venueByLocationFlow.value = Resource.Loading
        _venueByLocationFlow.value = repository.getVenueByLocation(lat, lng, sport)
    }

    fun getSportList() = viewModelScope.launch {
        _sportListFlow.value = Resource.Loading
        _sportListFlow.value = repository.getSportList()
    }
}