package com.devsinc.bws.model

data class TimeSlotCart(
    val court_name: String,
    val slot_date: String,
    val slot_price: Int,
    val time_slot: String,
    val time_slot_status: Int,
    val tslot_id: Int
)