package com.devsinc.bws.api

import com.devsinc.bws.model.*
import retrofit2.Response
import retrofit2.http.*

interface BookWithStarAPI {
    @POST("homescreen")
    suspend fun getHomeScreen(): Response<BookWithStarAPIresponse<HomeScreenData>>

    @GET("about")
    suspend fun getAbout(): Response<BookWithStarAPIresponse<AboutData>>

    @GET("get_class_activity")
    suspend fun getClassActivity(): Response<BookWithStarAPIresponse<List<ListItem>>>

    @FormUrlEncoded
    @POST("get_user_details")
    suspend fun getUserDetails(@Field("userid")userId: Int): Response<BookWithStarAPIresponse<CustomerDetails>>
}