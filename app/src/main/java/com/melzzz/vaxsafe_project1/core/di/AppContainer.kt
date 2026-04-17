package com.melzzz.vaxsafe_project1.core.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.melzzz.vaxsafe_project1.features.auth.data.repository.AuthRepositoryImpl
import com.melzzz.vaxsafe_project1.features.auth.data.repository.MockAuthRepositoryImpl
import com.melzzz.vaxsafe_project1.features.auth.domain.repository.AuthRepository
import com.melzzz.vaxsafe_project1.features.vaccines.data.repository.VaccineRepositoryImpl
import com.melzzz.vaxsafe_project1.features.vaccines.domain.repository.VaccineRepository

class AppContainer(context: Context) {

    val firestore: FirebaseFirestore by lazy { FirebaseFirestore.getInstance() }

    val firebaseAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }  // NUEVO

    // La nueva versión desconectada de Firebase:
    val authRepository: AuthRepository = MockAuthRepositoryImpl()

    val vaccineRepository: VaccineRepository by lazy {
        VaccineRepositoryImpl(firestore)
    }
}