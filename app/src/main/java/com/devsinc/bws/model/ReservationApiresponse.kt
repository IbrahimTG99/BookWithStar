package com.devsinc.bws.model

import com.google.gson.annotations.SerializedName

data class ReservationApiresponse<T>(
    @SerializedName("data")
    val data: T,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("rp")
    val rp: RewardPoints,

    @SerializedName("success")
    val success: Boolean? = null
)