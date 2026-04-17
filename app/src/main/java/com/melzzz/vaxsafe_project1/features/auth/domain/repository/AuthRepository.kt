package com.melzzz.vaxsafe_project1.features.auth.domain.repository

import com.melzzz.vaxsafe_project1.features.vaccines.domain.model.UserProfile

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<String>
    // 1. Agregamos name y bloodType al registro
    suspend fun register(email: String, password: String, name: String, bloodType: String): Result<String>
    fun getCurrentUserId(): String?
    fun logout()
    // 2. Nueva función para pedirle los datos al Dashboard
    suspend fun getCurrentUserProfile(): UserProfile?
}