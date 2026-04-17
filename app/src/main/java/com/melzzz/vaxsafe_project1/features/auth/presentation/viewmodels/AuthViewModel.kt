package com.melzzz.vaxsafe_project1.features.auth.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melzzz.vaxsafe_project1.features.auth.domain.repository.AuthRepository
import com.melzzz.vaxsafe_project1.features.auth.presentation.screens.AuthUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

// features/auth/presentation/viewmodels/AuthViewModel.kt
class AuthViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(AuthUiState())
    val uiState = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            authRepository.login(email, password).fold(
                onSuccess = { _uiState.update { it.copy(isLoading = false, isAuthenticated = true) } },
                onFailure = { e -> _uiState.update { it.copy(isLoading = false, error = e.message) } }
            )
        }
    }

    fun register(email: String, password: String, name: String, bloodType: String) {
        _uiState.update { it.copy(isLoading = true, error = null) }
        viewModelScope.launch {
            authRepository.register(email, password, name, bloodType).fold(
                onSuccess = {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            isLoginMode = true,
                            error = "¡Registro exitoso! Ahora inicia sesión."
                        )
                    }
                },
                onFailure = { e ->
                    _uiState.update { it.copy(isLoading = false, error = e.message) }
                }
            )
        }
    }

    fun toggleMode() {
        _uiState.update { it.copy(isLoginMode = !it.isLoginMode, error = null) }
    }

    // Agrega esto al final de AuthViewModel.kt
    fun resetState() {
        // Devuelve el estado a su forma original (falso)
        _uiState.update { AuthUiState() }
    }
}


