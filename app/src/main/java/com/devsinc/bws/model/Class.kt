package com.devsinc.bws.model

data class Class(
    val cid: Int,
    val class_address: String,
    val class_ammenities: List<ClassAmmenity>,
    val class_choose: List<String>,
    val class_detail: String,
    val class_gmap: String,
    val class_icon: List<String>,
    val class_name: String,
    val class_slider: List<String>,
    val schedule: List<Schedule>,
    val venue_timing: String
)