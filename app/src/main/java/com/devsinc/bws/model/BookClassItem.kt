package com.devsinc.bws.model

data class BookClassItem(
    val features: List<String>,
    val frequency_name: String,
    val pacid: Int,
    val package_name: String,
    val package_price: Float
)