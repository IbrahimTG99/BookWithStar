package com.devsinc.bws.model

data class Customer(
    val cus_country: String,
    val cus_email: String,
    val cus_id: Int,
    val cus_mobile: String,
    val cus_photo: String,
    val first_name: String,
    val last_name: String,
    val token: String
)