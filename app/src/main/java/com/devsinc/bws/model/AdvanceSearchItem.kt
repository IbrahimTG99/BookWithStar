package com.devsinc.bws.model

data class AdvanceSearchItem(
    val courts: List<Court>,
    val sid: Int,
    val venue_name: String,
    val venue_slider: List<String>
)