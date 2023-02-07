package com.devsinc.bws.model

import com.google.gson.annotations.SerializedName

data class OmniString(
    @SerializedName("scalar")
    val datum: String
)