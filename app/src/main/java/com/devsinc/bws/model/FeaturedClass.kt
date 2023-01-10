package com.devsinc.bws.model

data class FeaturedClass(
    val cid: Int,
    val class_icon: List<String>,
    val class_image: String,
    val class_location: String,
    val class_name: String,
    val class_rating: Int,
    val class_slag: String,
    val class_tag: List<String>
)