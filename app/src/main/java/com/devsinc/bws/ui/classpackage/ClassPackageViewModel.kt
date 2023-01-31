package com.devsinc.bws.ui.classpackage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsinc.bws.model.BookClassItem
import com.devsinc.bws.repository.CustomerRepository
import com.devsinc.bws.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ClassPackageViewModel @Inject constructor(
    private val repository: CustomerRepository
) : ViewModel() {
    private val _classPackageFlow = MutableStateFlow<Resource<List<BookClassItem>>?>(null)
    val classPackageFlow = _classPackageFlow.asStateFlow()

    fun getClassPackage(classId: Int) = viewModelScope.launch {
        _classPackageFlow.value = Resource.Loading
        _classPackageFlow.value = repository.getBookClass(classId)
    }
}