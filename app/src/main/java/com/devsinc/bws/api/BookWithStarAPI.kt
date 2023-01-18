package com.devsinc.bws.api

import com.devsinc.bws.model.*
import retrofit2.Response
import retrofit2.http.*

interface BookWithStarAPI {
    @POST("homescreen")
    suspend fun getHomeScreen(): Response<BookWithStarAPIresponse<HomeScreenData>>

    @GET("about")
    suspend fun getAbout(): Response<BookWithStarAPIresponse<AboutData>>

    @POST("get_stadium_details")
    suspend fun getStadiumDetails(@Query("venueid") venueId: Int): Response<BookWithStarAPIresponse<VenueDetail>>

    @POST("get_venue_by_Location")
    suspend fun getVenueByLocation(@Body params: VenueByLocationParams): Response<BookWithStarAPIresponse<List<Venue>>>

    @GET("sports")
    suspend fun getSportList(): Response<BookWithStarAPIresponse<List<DropdownListItem>>>

    @POST("get_class_activity")
    suspend fun getClassActivity(): Response<BookWithStarAPIresponse<List<DropdownListItem>>>

    @POST("findclasses")
    suspend fun findClasses(@Body params: FindClassesParams): Response<BookWithStarAPIresponse<List<ClassListItem>>>

    @FormUrlEncoded
    @POST("get_user_details")
    suspend fun getUserDetails(@Field("userid") userId: Int): Response<BookWithStarAPIresponse<CustomerDetails>>

    @GET("coupninfo")
    suspend fun getOffers(): Response<BookWithStarAPIresponse<List<Offer>>>

    @POST("bookclass")
    suspend fun getBookClass(): Response<BookWithStarAPIresponse<List<BookClassItem>>>
}