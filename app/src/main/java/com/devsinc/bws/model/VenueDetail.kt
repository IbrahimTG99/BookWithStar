package com.devsinc.bws.model

data class VenueDetail(
    val latitude: String,
    val longitude: String,
    val sid: Int,
    val venue_address: String,
    val venue_ammenities: List<VenueAmmenity>,
    val venue_choose: Any,
    val venue_detail: String,
    val venue_email: String,
    val venue_gmap: String,
    val venue_icon: List<String>,
    val venue_name: String,
    val venue_phone: String,
    val venue_slag: String,
    val venue_slider: List<String>,
    val venue_timing: String
)