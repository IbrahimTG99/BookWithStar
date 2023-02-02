package com.devsinc.bws.model

data class Court(
    val cid: Int,
    val court_name: String,
    val default_court: Int,
    val frequency: Int,
    val max_frequency: Int,
    val max_slot_select: Int,
    val min_frequency: Int,
    val special_frequency: List<String>
)