package com.devsinc.bws.ui.bookonline

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsinc.bws.model.AdvanceSearchItem
import com.devsinc.bws.model.TimeSlot
import com.devsinc.bws.model.TimeSlotParams
import com.devsinc.bws.model.Venue
import com.devsinc.bws.repository.CustomerRepository
import com.devsinc.bws.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookOnlineViewModel @Inject constructor(
    private val repository: CustomerRepository
) : ViewModel() {
    private val _venueByLocationFlow = MutableStateFlow<Resource<List<Venue>>?>(null)
    val venueByLocationFlow = _venueByLocationFlow.asStateFlow()

    private val _courtListFlow = MutableStateFlow<Resource<AdvanceSearchItem>?>(null)
    val courtListFlow = _courtListFlow.asStateFlow()

    private val _timeSlotsFlow = MutableStateFlow<Resource<List<TimeSlot>>?>(null)
    val timeSlotFlow = _timeSlotsFlow.asStateFlow()

    fun getVenueByLocation(lat: String, lng: String, sport: Int?) = viewModelScope.launch {
        _venueByLocationFlow.value = Resource.Loading
        _venueByLocationFlow.value = repository.getVenueByLocation(lat, lng, sport)
    }

    fun getCourtList(venueId: Int, sportId: Int) = viewModelScope.launch {
        _courtListFlow.value = Resource.Loading
        _courtListFlow.value = repository.advanceSearch(venueId, sportId)
    }

    fun getTimeSlots(courtId: Int, date: String, time: String) = viewModelScope.launch {
        _timeSlotsFlow.value = Resource.Loading
        _timeSlotsFlow.value = repository.getTimeSlots(TimeSlotParams(courtId, date, time))
    }
}