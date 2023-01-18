package com.devsinc.bws.model

data class LoginParams(
    val username: String,
    val password: String,
    val refreshedtoken: String,
    val devicetype: String
)