package com.devsinc.bws.model

data class FindPlayerItem(
    val avaibility_list: List<DropdownListItem>,
    val gender_list: List<DropdownListItem>,
    val player_email: String,
    val player_mobile: String,
    val player_name: String,
    val player_photo: String,
    val player_tag: List<String>,
    val skill_list: List<DropdownListItem>,
    val sports_list: List<DropdownListItem>,
    val venue_list: List<DropdownListItem>
)