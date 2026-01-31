package com.melzzz.vaxsafe_project1.features.vaccines.domain.model

data class Vaccine(
    val id: String = "",
    val name: String,
    val laboratory: String,
    val date: String,
    val status: VaccineStatus
)