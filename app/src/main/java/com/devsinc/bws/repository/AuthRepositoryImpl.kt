package com.devsinc.bws.repository

import android.util.Log
import android.widget.Toast
import com.devsinc.bws.model.BookWithStarAPIresponse
import com.devsinc.bws.model.Customer
import com.devsinc.bws.model.LoginParams
import com.devsinc.bws.retrofit.BookWithStarAPI
import com.google.gson.Gson
import org.json.JSONObject
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val bookWithStarAPI: BookWithStarAPI
) : AuthRepository {
    override var customer: Customer? = null;

    override suspend fun login(credentials: LoginParams): Resource<Customer> {
        return try {
            val response = bookWithStarAPI.login(credentials)
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
                val error = Gson().fromJson(response.errorBody()?.charStream(), BookWithStarAPIresponse::class.java)
                Resource.Error(Exception(error.message.toString()))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun logout() {
        // @TODO: Implement logout
    }
}