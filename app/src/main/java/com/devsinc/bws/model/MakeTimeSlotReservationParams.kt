package com.devsinc.bws.model

data class MakeTimeSlotReservationParams(
    val slotid: Int,
    val frequency: Int,
    val max_frequency: Int
)
