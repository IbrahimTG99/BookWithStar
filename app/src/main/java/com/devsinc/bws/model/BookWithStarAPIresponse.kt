package com.devsinc.bws.model

import com.google.gson.annotations.SerializedName

data class BookWithStarAPIresponse<T> (
    @SerializedName("success")
    val success: Boolean? = null,

    @SerializedName("message")
    val message: String? = null,

    @SerializedName("data")
    val data: T
)