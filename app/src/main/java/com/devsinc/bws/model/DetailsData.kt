package com.devsinc.bws.model

sealed class DetailsData {
    data class Info(
        val title: String,
        val icon: String,
        val isOnline: Boolean
    ) : DetailsData()

    data class Heading(
        val title: String
    ) : DetailsData()
}
