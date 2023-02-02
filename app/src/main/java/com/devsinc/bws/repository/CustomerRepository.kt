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

    suspend fun getBookClass(classId: Int): Resource<List<BookClassItem>>

    suspend fun getPushNotificationData(): Resource<String>

    suspend fun getMyBookings(): Resource<List<BookingItem>>

    suspend fun getVenueDetail(venueId: Int): Resource<VenueDetail>

    suspend fun getClassDetail(classId: Int): Resource<ClassDetail>

    suspend fun getContactInfo(): Resource<ContactInfo>

    suspend fun getSportType(sportId: Int): Resource<List<DropdownListItem>>

    suspend fun findPlayer(params: FindPlayerParams): Resource<List<FindPlayerItem>>

    suspend fun getPlayerFinderCommonData(): Resource<PlayerFinderCommonData>

    suspend fun advanceSearch(venueId: Int, sportId: Int): Resource<AdvanceSearchItem>

    suspend fun getTimeSlots(params: TimeSlotParams): Resource<List<TimeSlot>>
}