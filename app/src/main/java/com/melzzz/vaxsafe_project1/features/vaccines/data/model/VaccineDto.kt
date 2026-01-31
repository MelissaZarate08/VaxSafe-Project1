package com.melzzz.vaxsafe_project1.features.vaccines.data.model

data class VaccineDto(
    val id: String = "",
    val name: String = "",
    val laboratory: String = "",
    val date: String = "",
    val status: String = "PENDING"
)
