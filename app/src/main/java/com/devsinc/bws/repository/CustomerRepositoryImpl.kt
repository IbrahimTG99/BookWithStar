package com.devsinc.bws.repository

import com.devsinc.bws.model.Customer
import com.devsinc.bws.model.HomeScreenData
import com.devsinc.bws.retrofit.BookWithStarAPI
import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor(
    private val bookWithStarAPI: BookWithStarAPI
) : CustomerRepository {
    override var customer: Customer? = null;

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

    override suspend fun getCustomer(): Resource<Customer> {
        return try {
            val response = bookWithStarAPI.getUserDetails()
            if (response.isSuccessful) {
                response.body()?.let { result ->
                    if (result.success == true) {
                        customer = result.data
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