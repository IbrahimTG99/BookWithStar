package com.devsinc.bws.ui.bookclass

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsinc.bws.model.BookClassItem
import com.devsinc.bws.model.DropdownListItem
import com.devsinc.bws.model.Offer
import com.devsinc.bws.repository.CustomerRepository
import com.devsinc.bws.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookClassViewModel @Inject constructor(
    private val repository: CustomerRepository
) : ViewModel() {
    private val _bookClassFlow = MutableStateFlow<Resource<List<BookClassItem>>?>(null)
    val bookClassFlow = _bookClassFlow.asStateFlow()

    private val _sportListFlow = MutableStateFlow<Resource<List<DropdownListItem>>?>(null)
    val sportListFlow = _sportListFlow.asStateFlow()

    private val _sportTypeListFlow = MutableStateFlow<Resource<List<DropdownListItem>>?>(null)
    val sportTypeListFlow = _sportTypeListFlow.asStateFlow()


    fun getBookClass(classId: Int) = viewModelScope.launch {
        _bookClassFlow.value = Resource.Loading
        _bookClassFlow.value = repository.getBookClass(classId)
    }

    fun getSportList() = viewModelScope.launch {
        _sportListFlow.value = Resource.Loading
        _sportListFlow.value = repository.getClassActivity()
    }

    fun getSportTypeList(sportId: Int) = viewModelScope.launch {
        _sportTypeListFlow.value = Resource.Loading
        _sportTypeListFlow.value = repository.getSportType(sportId)
    }
}