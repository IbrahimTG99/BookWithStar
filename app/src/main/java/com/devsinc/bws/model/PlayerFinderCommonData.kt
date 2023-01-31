package com.devsinc.bws.model

data class PlayerFinderCommonData(
    val age_group_list: List<DropdownListItem>,
    val avaibility: List<DropdownListItem>,
    val gender_list: List<DropdownListItem>,
    val player_interest: List<DropdownListItem>,
    val player_location: List<DropdownListItem>,
    val player_type: List<DropdownListItem>,
    val skill_list: List<DropdownListItem>,
    val venue_list: List<DropdownListItem>
)