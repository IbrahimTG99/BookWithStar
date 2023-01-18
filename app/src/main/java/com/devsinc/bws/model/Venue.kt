package com.devsinc.bws.model

data class Venue(
    val distance: String,
    val sid: Int,
    val venue_icon: List<String>,
    val venue_image: String,
    val venue_location: String,
    val venue_name: String,
    val venue_sports: List<VenueSport>
)