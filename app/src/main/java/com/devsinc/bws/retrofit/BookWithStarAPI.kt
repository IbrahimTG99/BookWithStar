package com.devsinc.bws.retrofit

import com.devsinc.bws.model.*
import com.devsinc.bws.repository.Resource
import retrofit2.Response
import retrofit2.http.*

interface BookWithStarAPI {
    @POST("login")
    suspend fun login(@Body params: LoginParams): Response<BookWithStarAPIresponse<Customer>>

    @Headers("Authorization")
    @POST("homescreen")
    suspend fun getHomeScreen(): Response<BookWithStarAPIresponse<HomeScreenData>>

    @GET("about")
    suspend fun getAbout(): Response<BookWithStarAPIresponse<AboutData>>

    @GET("get_class_activity")
    suspend fun getClassActivity(): Response<BookWithStarAPIresponse<List<ListItem>>>

    @GET("get_user_details")
    suspend fun getUserDetails(): Response<BookWithStarAPIresponse<Customer>>
}