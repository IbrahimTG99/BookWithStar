package com.devsinc.bws.repository

import androidx.lifecycle.LiveData
import com.devsinc.bws.model.Customer
import com.devsinc.bws.model.LoginParams
import kotlinx.coroutines.flow.StateFlow

interface AuthRepository {
    var customer : Customer?
    suspend fun login(credentials: LoginParams) : Resource<Customer>
    suspend fun logout()
}