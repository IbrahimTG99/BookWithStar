package com.devsinc.bws.model

data class HomeScreenData(
    val featured_class: List<FeaturedClass>,
    val homeimage: String,
    val stadium: List<FeaturedVenue>,
    val title: String,
    val total_reward_point: Int
)