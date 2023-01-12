package com.devsinc.bws.model

data class FeaturedVenue(
    val venue_icon: List<String>,
    val venue_id: Int,
    val venue_image: String,
    val venue_location: String,
    val venue_name: String,
    val venue_phone: String,
    val venue_rating: Float,
    val venue_slag: String
)