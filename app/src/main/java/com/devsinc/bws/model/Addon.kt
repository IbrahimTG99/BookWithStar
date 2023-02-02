package com.devsinc.bws.model

import com.google.gson.annotations.SerializedName

data class Addon(
    val item_category: Int,
    @SerializedName(value = "item_class", alternate = ["item_venue"])
    val item_location: Int,
    val item_id: Int,
    val item_name: String,
    val item_price: Int,
    val item_stock: Int,
    val item_weight: String
)