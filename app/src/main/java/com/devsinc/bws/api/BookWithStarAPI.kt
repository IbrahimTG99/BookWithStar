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

    @POST("get_class_details")
    suspend fun getClassDetails(@Query("classid") classId: Int): Response<BookWithStarAPIresponse<ClassDetail>>

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
    suspend fun getBookClass(@Query("classid") classId: Int): Response<BookWithStarAPIresponse<List<BookClassItem>>>

    @POST("get_push_notfication_data")
    suspend fun getPushNotificationData(): Response<BookWithStarAPIresponse<String>>

    @POST("my_booking")
    suspend fun getMyBookings(@Query("customer_id") customerId: Int): Response<BookWithStarAPIresponse<List<BookingItem>>>

    @GET("getcontactinfo")
    suspend fun getContactInfo(): Response<BookWithStarAPIresponse<ContactInfo>>

    @POST("get_sport_type")
    suspend fun getSportType(@Query("sportid") sportId: Int): Response<BookWithStarAPIresponse<List<DropdownListItem>>>

    @POST("find_player")
    suspend fun findPlayer(@Body params: FindPlayerParams): Response<BookWithStarAPIresponse<List<FindPlayerItem>>>

    @POST("get_player_finder_common_data")
    suspend fun getPlayerFinderCommonData(): Response<BookWithStarAPIresponse<PlayerFinderCommonData>>
}