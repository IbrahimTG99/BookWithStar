package com.devsinc.bws.model

data class FindPlayerParams(
    val sport: Int,
    val gender: Int,
    val avaibility: Int,
    val skill: Int,
    val venue: Int
)
