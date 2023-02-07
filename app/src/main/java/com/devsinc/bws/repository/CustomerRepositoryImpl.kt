package com.devsinc.bws.repository

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import com.devsinc.bws.api.BookWithStarAPI
import com.devsinc.bws.data.CustomerDao
import com.devsinc.bws.model.*
import com.devsinc.bws.utils.NetworkConstants
import dagger.hilt.android.internal.Contexts.getApplication
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject

class CustomerRepositoryImpl @Inject constructor(
    private val bookWithStarAPI: BookWithStarAPI, private val customerDao: CustomerDao
) : CustomerRepository {
    override var customer: Customer? = null

    init {
        CoroutineScope(Dispatchers.IO).launch { customer = customerDao.getCustomer() }
            .invokeOnCompletion {
                NetworkConstants.TOKEN = customer?.token ?: ""
                NetworkConstants.CUSTOMERID = customer?.cus_id ?: 0
            }
    }

    override suspend fun getHomeScreen(): Resource<HomeScreenData> {
        return try {
            getResponse(bookWithStarAPI.getHomeScreen())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }


    override suspend fun getCustomerDetails(): Resource<CustomerDetails> {
        if (customer == null) {
            customer = customerDao.getCustomer()
        }
        return try {
            getResponse(bookWithStarAPI.getUserDetails(customer!!.cus_id))
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getVenueByLocation(
        lat: String, lng: String, sport: Int?
    ): Resource<List<Venue>> {
        return try {
            getResponse(bookWithStarAPI.getVenueByLocation(VenueByLocationParams(lat, lng, sport)))
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getSportList(): Resource<List<DropdownListItem>> {
        return try {
            getResponse(bookWithStarAPI.getSportList())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun findClasses(
        lat: String, lng: String, sport: Int?
    ): Resource<List<ClassListItem>> {
        return try {
            getResponse(bookWithStarAPI.findClasses(FindClassesParams(lat, lng, sport)))
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getClassActivity(): Resource<List<DropdownListItem>> {
        return try {
            getResponse(bookWithStarAPI.getClassActivity())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getOffers(): Resource<List<Offer>> {
        return try {
            getResponse(bookWithStarAPI.getOffers())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getBookClass(classId: Int): Resource<List<BookClassItem>> {
        return try {
            getResponse(bookWithStarAPI.getBookClass(classId))
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getPushNotificationData(): Resource<String> {
        return try {
            getResponse(bookWithStarAPI.getPushNotificationData())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getMyBookings(): Resource<List<BookingItem>> {
        if (customer == null) {
            customer = customerDao.getCustomer()
        }
        return try {
            getResponse(bookWithStarAPI.getMyBookings(customer!!.cus_id))
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getVenueDetail(venueId: Int): Resource<VenueDetail> {
        return try {
            getResponse(bookWithStarAPI.getStadiumDetails(venueId))
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getClassDetail(classId: Int): Resource<ClassDetail> {
        return try {
            getResponse(bookWithStarAPI.getClassDetails(classId))
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getContactInfo(): Resource<ContactInfo> {
        return try {
            getResponse(bookWithStarAPI.getContactInfo())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getSportType(sportId: Int): Resource<List<DropdownListItem>> {
        return try {
            getResponse(bookWithStarAPI.getSportType(sportId))
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun findPlayer(params: FindPlayerParams): Resource<List<FindPlayerItem>> {
        return try {
            getResponse(bookWithStarAPI.findPlayer(params))
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getPlayerFinderCommonData(): Resource<PlayerFinderCommonData> {
        return try {
            getResponse(bookWithStarAPI.getPlayerFinderCommonData())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun advanceSearch(venueId: Int, sportId: Int): Resource<AdvanceSearchItem> {
        return try {
            getResponse(bookWithStarAPI.advanceSearch(venueId, sportId))
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getTimeSlots(params: TimeSlotParams): Resource<List<TimeSlot>> {
        return try {
            getResponse(bookWithStarAPI.getTimeSlot(params))
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun getTimeSlotReservation(
        slotIds: String,
        customerId: Int
    ): Resource<List<TimeSlotCart>> {
        return try {
            getResponse(bookWithStarAPI.getTimeSlotReservation(slotIds, customerId))
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun makeTimeSlotReservation(params: MakeTimeSlotReservationParams): Resource<OmniString> {
        return try {
            getResponse(bookWithStarAPI.makeTimeSlotReservation(params))
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    override suspend fun changeCartStatus(
        slotId: String,
        slotStatus: Int
    ): Resource<OmniString?> {
        return try {
            getResponseMsg(bookWithStarAPI.changeCartStatus(slotId, slotStatus))
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    private fun getResponseMsg(response: Response<BookWithStarAPIresponse<OmniString?>>): Resource<OmniString?> {
        return if (response.isSuccessful) {
            response.body()?.let { result ->
                if (result.success == true) {
                    Resource.Success(result.message?.let { OmniString(it) })
                } else {
                    Resource.Error(Exception(result.message))
                }
            } ?: Resource.Error(Exception("An unknown error occurred"))
        } else {
            Resource.Error(
                Exception(
                    JSONObject(
                        response.errorBody()!!.charStream().readText()
                    ).getString("message")
                )
            )
        }
    }

    private fun <T> getResponse(response: Response<BookWithStarAPIresponse<T>>): Resource<T> {0
        return if (response.isSuccessful) {
            response.body()?.let { result ->
                if (result.success == true) {
                    Resource.Success(result.data)
                } else {
                    Resource.Error(Exception(result.message))
                }
            } ?: Resource.Error(Exception("An unknown error occurred"))
        } else {
            Resource.Error(
                Exception(
                    JSONObject(
                        response.errorBody()!!.charStream().readText()
                    ).getString("message")
                )
            )
        }
    }
}