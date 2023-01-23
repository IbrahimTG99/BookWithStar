package com.devsinc.bws.repository

import com.devsinc.bws.model.*

interface CustomerRepository {
    var customer: Customer?
    suspend fun getHomeScreen(): Resource<HomeScreenData>
    suspend fun getCustomerDetails(): Resource<CustomerDetails>
    suspend fun getVenueByLocation(lat: String, lng: String, sport: Int?): Resource<List<Venue>>
    suspend fun getSportList(): Resource<List<DropdownListItem>>

    suspend fun findClasses(lat: String, lng: String, sport: Int?): Resource<List<ClassListItem>>

    suspend fun getClassActivity(): Resource<List<DropdownListItem>>

    suspend fun getOffers(): Resource<List<Offer>>

    suspend fun getBookClass(): Resource<List<BookClassItem>>

    suspend fun getPushNotificationData(): Resource<String>

    suspend fun getMyBookings(): Resource<List<BookingItem>>

    suspend fun getVenueDetail(venueId: Int): Resource<VenueDetail>

    suspend fun getClassDetail(classId: Int): Resource<ClassDetail>
}