package com.melzzz.vaxsafe_project1.features.auth.presentation.screens

// features/auth/presentation/screens/AuthUiState.kt
data class AuthUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val isAuthenticated: Boolean = false,
    val isLoginMode: Boolean = true  // toggle login/registro
)