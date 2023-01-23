package com.devsinc.bws.ui.classdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsinc.bws.model.ClassDetail
import com.devsinc.bws.repository.CustomerRepository
import com.devsinc.bws.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassDetailsViewModel @Inject constructor(
    private val repository: CustomerRepository
) : ViewModel() {
    private val _classDetailsFlow = MutableStateFlow<Resource<ClassDetail>?>(null)
    val classDetailsFlow = _classDetailsFlow.asStateFlow()

    fun getClassDetails(classId: Int) = viewModelScope.launch {
        _classDetailsFlow.value = Resource.Loading
        _classDetailsFlow.value = repository.getClassDetail(classId)
    }
}