package com.melzzz.vaxsafe_project1.features.auth.data.repository

import com.melzzz.vaxsafe_project1.features.auth.domain.repository.AuthRepository
import com.melzzz.vaxsafe_project1.features.vaccines.domain.model.UserProfile
import kotlinx.coroutines.delay

class MockAuthRepositoryImpl : AuthRepository {

    // Ahora guardamos la contraseña y el perfil del usuario
    private data class UserData(val password: String, val profile: UserProfile)

    private val fakeDatabase = mutableMapOf<String, UserData>()
    private var currentUserId: String? = null // Usaremos el email como ID

    override suspend fun login(email: String, password: String): Result<String> {
        delay(800)
        val userData = fakeDatabase[email]
        return if (userData != null && userData.password == password) {
            currentUserId = email
            Result.success(email)
        } else {
            Result.failure(Exception("Credenciales incorrectas o usuario no registrado."))
        }
    }

    override suspend fun register(email: String, password: String, name: String, bloodType: String): Result<String> {
        delay(800)
        if (fakeDatabase.containsKey(email)) {
            return Result.failure(Exception("Este correo ya está registrado."))
        }

        // Creamos su perfil y lo guardamos en nuestra base de datos falsa
        val newProfile = UserProfile(name = name, bloodType = bloodType)
        fakeDatabase[email] = UserData(password, newProfile)
        currentUserId = email

        return Result.success(email)
    }

    override fun getCurrentUserId(): String? = currentUserId

    override fun logout() {
        currentUserId = null
    }

    // El Dashboard usará esto para saber a quién saludar
    override suspend fun getCurrentUserProfile(): UserProfile? {
        delay(300)
        return currentUserId?.let { uid ->
            fakeDatabase[uid]?.profile
        }
    }
}