package com.melzzz.vaxsafe_project1.features.vaccines.presentation.screens

import com.melzzz.vaxsafe_project1.features.vaccines.domain.model.UserProfile
import com.melzzz.vaxsafe_project1.features.vaccines.domain.model.Vaccine

data class DashboardUiState(
    val isLoading: Boolean = false,
    val vaccines: List<Vaccine> = emptyList(),
    val userProfile: UserProfile = UserProfile(),
    val error: String? = null,
    val showAddDialog: Boolean = false,
    val showMapSheet: Boolean = false
)