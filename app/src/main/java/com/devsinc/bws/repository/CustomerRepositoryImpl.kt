package com.devsinc.bws.repository

import com.devsinc.bws.api.BookWithStarAPI
import com.devsinc.bws.data.CustomerDao
import com.devsinc.bws.model.*
import com.devsinc.bws.utils.NetworkConstants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor(
    private val bookWithStarAPI: BookWithStarAPI, private val customerDao: CustomerDao
) : CustomerRepository {
    override var customer: Customer? = null

    init {
        CoroutineScope(Dispatchers.IO).launch { customer = customerDao.getCustomer() }
            .invokeOnCompletion {
                NetworkConstants.TOKEN = customer?.token ?: ""
            }
    }

    override suspend fun getHomeScreen(): Resource<HomeScreenData> {
        val response = bookWithStarAPI.getHomeScreen()
        return getResponse(response)
    }

    override suspend fun getCustomerDetails(): Resource<CustomerDetails> {
        if (customer == null) {
            customer = customerDao.getCustomer()
        }
        val response = bookWithStarAPI.getUserDetails(customer!!.cus_id)
        return getResponse(response)
    }

    override suspend fun getVenueByLocation(
        lat: String, lng: String, sport: Int?
    ): Resource<List<Venue>> {
        val response = bookWithStarAPI.getVenueByLocation(VenueByLocationParams(lat, lng, sport))
        return getResponse(response)
    }

    override suspend fun getSportList(): Resource<List<DropdownListItem>> {
        val response = bookWithStarAPI.getSportList()
        return getResponse(response)
    }

    override suspend fun findClasses(
        lat: String, lng: String, sport: Int?
    ): Resource<List<ClassListItem>> {
        val response = bookWithStarAPI.findClasses(FindClassesParams(lat, lng, sport))
        return getResponse(response)
    }

    override suspend fun getClassActivity(): Resource<List<DropdownListItem>> {
        val response = bookWithStarAPI.getClassActivity()
        return getResponse(response)
    }

    override suspend fun getOffers(): Resource<List<Offer>> {
        val response = bookWithStarAPI.getOffers()
        return getResponse(response)
    }

    override suspend fun getBookClass(classId: Int): Resource<List<BookClassItem>> {
        val response = bookWithStarAPI.getBookClass(classId)
        return getResponse(response)
    }

    override suspend fun getPushNotificationData(): Resource<String> {
        val response = bookWithStarAPI.getPushNotificationData()
        return getResponse2(response)
    }

    override suspend fun getMyBookings(): Resource<List<BookingItem>> {
        if (customer == null) {
            customer = customerDao.getCustomer()
        }
        val response = bookWithStarAPI.getMyBookings(customer!!.cus_id)
        return getResponse2(response)
    }

    override suspend fun getVenueDetail(venueId: Int): Resource<VenueDetail> {
        val response = bookWithStarAPI.getStadiumDetails(venueId)
        return getResponse(response)
    }

    override suspend fun getClassDetail(classId: Int): Resource<ClassDetail> {
        val response = bookWithStarAPI.getClassDetails(classId)
        return getResponse(response)
    }

    override suspend fun getContactInfo(): Resource<ContactInfo> {
        val response = bookWithStarAPI.getContactInfo()
        return getResponse(response)
    }

    override suspend fun getSportType(sportId: Int): Resource<List<DropdownListItem>> {
        val response = bookWithStarAPI.getSportType(sportId)
        return getResponse(response)
    }

    private fun <T> getResponse2(response: Response<BookWithStarAPIresponse<T>>): Resource<T> {
        return try {
            if (response.isSuccessful) {
                response.body()?.let { result ->
                    if (result.success == true) {
                        Resource.Success(result.data)
                    } else {
                        Resource.Error(Exception(result.message.toString()))
                    }
                } ?: Resource.Error(Exception("An unknown error occurred"))
            } else {
                Resource.Error(Exception("No Information Available"))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    private fun <T> getResponse(response: Response<BookWithStarAPIresponse<T>>): Resource<T> {
        return try {
            if (response.isSuccessful) {
                response.body()?.let { result ->
                    if (result.success == true) {
                        Resource.Success(result.data)
                    } else {
                        Resource.Error(Exception(result.message))
                    }
                } ?: Resource.Error(Exception("An unknown error occurred"))
            } else {
                Resource.Error(Exception(response.errorBody().toString()))
            }
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}