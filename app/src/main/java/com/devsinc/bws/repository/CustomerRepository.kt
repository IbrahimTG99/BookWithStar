package com.devsinc.bws.repository

import com.devsinc.bws.model.Customer
import com.devsinc.bws.model.CustomerDetails
import com.devsinc.bws.model.HomeScreenData

interface CustomerRepository {
    var customer : Customer?
    suspend fun getHomeScreen() : Resource<HomeScreenData>
    suspend fun getCustomerDetails() : Resource<CustomerDetails>
}