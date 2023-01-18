package com.devsinc.bws.api

import com.devsinc.bws.model.BookWithStarAPIresponse
import com.devsinc.bws.model.Customer
import com.devsinc.bws.model.LoginParams
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {
    @POST("login")
    suspend fun login(@Body params: LoginParams): Response<BookWithStarAPIresponse<Customer>>
}