package com.devsinc.bws.model

import com.google.gson.annotations.SerializedName

data class CustomerDetails(
    @SerializedName("cus_country")
    val cus_country: String,
    @SerializedName("cus_email")
    val cus_email: String,
    @SerializedName("cus_id")
    val cus_id: Int,
    @SerializedName("cus_mobile")
    val cus_mobile: String,
    @SerializedName("cus_name")
    val cus_name: String,
    @SerializedName("cus_photo")
    val cus_photo: String,
    @SerializedName("first_name")
    val first_name: String,
    @SerializedName("last_name")
    val last_name: String
)