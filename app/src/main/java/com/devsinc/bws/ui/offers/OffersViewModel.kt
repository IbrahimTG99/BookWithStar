package com.devsinc.bws.ui.offers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devsinc.bws.model.Offer
import com.devsinc.bws.repository.CustomerRepository
import com.devsinc.bws.repository.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OffersViewModel @Inject constructor(
    private val repository: CustomerRepository
) : ViewModel() {
    private val _offersFlow = MutableStateFlow<Resource<List<Offer>>?>(null)
    val offersFlow = _offersFlow.asStateFlow()

    fun getOffers() = viewModelScope.launch {
        _offersFlow.value = Resource.Loading
        _offersFlow.value = repository.getOffers()
    }
}