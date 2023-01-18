package com.devsinc.bws.repository

import com.devsinc.bws.api.AuthAPI
import com.devsinc.bws.data.CustomerDao
import com.devsinc.bws.model.BookWithStarAPIresponse
import com.devsinc.bws.model.Customer
import com.devsinc.bws.model.LoginParams
import com.devsinc.bws.api.BookWithStarAPI
import com.devsinc.bws.api.NetworkInterceptor
import com.devsinc.bws.utils.Constants
import com.devsinc.bws.utils.NetworkConstants
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val bookWithStarAPI: AuthAPI,
    private val customerDao: CustomerDao
) : AuthRepository {

    override var customer: Customer? = null;

    override suspend fun isCustomerLoggedIn(): Boolean {
        customer = customerDao.getCustomer()
        return customer != null
    }


    override suspend fun login(credentials: LoginParams): Resource<Customer> {
        return try {
            val response = bookWithStarAPI.login(credentials)
            if (response.isSuccessful) {
                response.body()?.let { result ->
                    if (result.success == true) {
                        val customer = result.data
                        customerDao.insertCustomer(customer)
                        NetworkConstants.TOKEN = customer.token
                        Resource.Success(customer)
                    } else {
                        Resource.Error(Exception(result.message))
                    }
                } ?: Resource.Error(Exception("An unknown error occurred"))
            } else {
                val error = Gson().fromJson(response.errorBody()?.charStream(), BookWithStarAPIresponse::class.java)
                Resource.Error(Exception(error.message.toString()))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun logout(): Resource<Unit> {

        NetworkConstants.TOKEN = ""
        Constants.isUserLoggedIn = false
        customer = null
        return Resource.Success(customerDao.deleteCustomer())
    }
}