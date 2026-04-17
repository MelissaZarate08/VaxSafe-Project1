package com.melzzz.vaxsafe_project1.features.auth.domain.model

// features/auth/domain/model/AuthResult.kt
sealed class AuthResult {
    data class Success(val uid: String) : AuthResult()
    data class Failure(val message: String) : AuthResult()
}