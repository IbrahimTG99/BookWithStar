package com.devsinc.bws.repository

import android.util.Log
import com.devsinc.bws.api.BookWithStarAPI
import com.devsinc.bws.data.CustomerDao
import com.devsinc.bws.model.Customer
import com.devsinc.bws.model.CustomerDetails
import com.devsinc.bws.model.HomeScreenData
import com.devsinc.bws.utils.NetworkConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor(
    private val bookWithStarAPI: BookWithStarAPI,
    private val customerDao: CustomerDao
) : CustomerRepository {
    override var customer: Customer? = null;

    init {
        CoroutineScope(Dispatchers.IO).launch { customer = customerDao.getCustomer() }
            .invokeOnCompletion {
                NetworkConstants.TOKEN = customer?.token ?: ""
            }
    }

    override suspend fun getHomeScreen(): Resource<HomeScreenData> {
        return try {
            val response = bookWithStarAPI.getHomeScreen()
            if (response.isSuccessful) {
                response.body()?.let { result ->
                    if (result.success == true) {
                        Resource.Success(result.data)
                    } else {
                        Resource.Error(Exception(result.message))
                    }
                } ?: Resource.Error(Exception("An unknown error occurred"))
            } else {
                Resource.Error(Exception(response.errorBody().toString()))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getCustomerDetails(): Resource<CustomerDetails> {
        return try {
            if (customer == null){
                customer= customerDao.getCustomer()
            }
            val response = bookWithStarAPI.getUserDetails(customer!!.cus_id)
            if (response.isSuccessful) {
                response.body()?.let { result ->
                    if (result.success == true) {
                        Resource.Success(result.data)
                    } else {
                        Resource.Error(Exception(result.message))
                    }
                } ?: Resource.Error(Exception("An unknown error occurred"))
            } else {
                Resource.Error(Exception(response.errorBody().toString()))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}