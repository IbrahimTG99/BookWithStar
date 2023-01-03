package com.devsinc.bws.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.devsinc.bws.model.Customer
import com.devsinc.bws.retrofit.BookWithStarAPI
import javax.inject.Inject

class CustomerRepository @Inject constructor(private val bookWithStarAPI: BookWithStarAPI) {

    private val _customer = MutableLiveData<Customer>()
    val customer : LiveData<Customer>
    get() = _customer

    suspend fun login() {
        val response = bookWithStarAPI.login()
        if (response.isSuccessful && response.body() != null) {
            _customer.postValue(response.body())
        }
    }
}