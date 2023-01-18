package com.devsinc.bws.ui.findclass

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsinc.bws.model.ClassListItem
import com.devsinc.bws.model.DropdownListItem
import com.devsinc.bws.repository.CustomerRepository
import com.devsinc.bws.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class FindClassViewModel @Inject constructor(
    private val repository: CustomerRepository
) : ViewModel() {
    private val _classSearchFlow = MutableStateFlow<Resource<List<ClassListItem>>?>(null)
    val classSearchFlow = _classSearchFlow.asStateFlow()

    private val _classActivityListFlow = MutableStateFlow<Resource<List<DropdownListItem>>?>(null)
    val classActivityListFlow = _classActivityListFlow.asStateFlow()

    fun getClassSearchResult(lat: String, lng: String, sport: Int?) = viewModelScope.launch {
        _classSearchFlow.value = Resource.Loading
        _classSearchFlow.value = repository.findClasses(lat, lng, sport)
    }

    fun getClassActivityList() = viewModelScope.launch {
        _classActivityListFlow.value = Resource.Loading
        _classActivityListFlow.value = repository.getClassActivity()
    }
}