package com.devsinc.bws.ui.contactus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsinc.bws.model.ContactInfo
import com.devsinc.bws.repository.CustomerRepository
import com.devsinc.bws.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ContactUsViewModel @Inject constructor(
    private val repository: CustomerRepository
) : ViewModel() {
    private val _contactInfoFlow = MutableStateFlow<Resource<ContactInfo>?>(null)
    val contactInfoFlow = _contactInfoFlow.asStateFlow()

    fun getContactInfo() = viewModelScope.launch {
        _contactInfoFlow.value = Resource.Loading
        _contactInfoFlow.value = repository.getContactInfo()
    }
}