package com.devsinc.bws.retrofit

import com.devsinc.bws.model.Customer
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST

interface BookWithStarAPI {
    @POST("login")
    suspend fun login(): Response<Customer>
}