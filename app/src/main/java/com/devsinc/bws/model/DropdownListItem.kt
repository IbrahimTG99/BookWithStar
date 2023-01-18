package com.devsinc.bws.model

import com.google.gson.annotations.SerializedName

data class DropdownListItem(
    @SerializedName(value="id", alternate= ["vid", "aid"])
    val id: Int,
    @SerializedName(value="name", alternate= ["venue_name", "activity_name"])
    val name: String
)
