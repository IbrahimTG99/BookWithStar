package com.devsinc.bws.ui.findteammates

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsinc.bws.model.*
import com.devsinc.bws.repository.CustomerRepository
import com.devsinc.bws.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@HiltViewModel
class FindTeammatesViewModel @Inject constructor(
    private val repository: CustomerRepository
) : ViewModel() {
    private val _findTeammatesFlow = MutableStateFlow<Resource<List<FindPlayerItem>>?>(null)
    val findTeammatesFlow = _findTeammatesFlow.asStateFlow()

    private val _playerFinderFLow = MutableStateFlow<Resource<PlayerFinderCommonData>?>(null)
    val playerFinderFLow = _playerFinderFLow.asStateFlow()

    lateinit var genderList: List<DropdownListItem>
    lateinit var availabilityList: List<DropdownListItem>
    lateinit var skillList: List<DropdownListItem>
    lateinit var venueList: List<DropdownListItem>
    lateinit var sportList: List<DropdownListItem>

    fun findPlayer(params: FindPlayerParams) = viewModelScope.launch {
        _findTeammatesFlow.value = Resource.Loading
        _findTeammatesFlow.value = repository.findPlayer(params)
    }

    fun getPlayerFinderData() = viewModelScope.launch {
        _playerFinderFLow.value = Resource.Loading
        _playerFinderFLow.value = repository.getPlayerFinderCommonData()
    }
}