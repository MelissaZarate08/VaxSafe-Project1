package com.melzzz.vaxsafe_project1.features.auth.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.melzzz.vaxsafe_project1.features.auth.domain.repository.AuthRepository
import com.melzzz.vaxsafe_project1.features.vaccines.domain.model.UserProfile
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val auth: FirebaseAuth
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<String> {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            Result.success(result.user!!.uid)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // 1. Actualizamos la firma para que acepte los nuevos parámetros y coincida con la interfaz
    override suspend fun register(
        email: String,
        password: String,
        name: String,
        bloodType: String
    ): Result<String> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            // Nota: Cuando quieras volver a usar Firebase 100% real, aquí deberás
            // guardar 'name' y 'bloodType' en una colección "users" de Firestore.
            Result.success(result.user!!.uid)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getCurrentUserId(): String? = auth.currentUser?.uid

    override fun logout() = auth.signOut()

    // 2. Agregamos la función faltante para que deje de marcar error
    override suspend fun getCurrentUserProfile(): UserProfile? {
        // Nota: Cuando vuelvas a Firebase, aquí leerías los datos de Firestore.
        // Por ahora devolvemos un perfil vacío para cumplir con el contrato de la interfaz.
        return null
    }
}