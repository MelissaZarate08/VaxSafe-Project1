package com.melzzz.vaxsafe_project1.core.di

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.melzzz.vaxsafe_project1.features.vaccines.data.repository.VaccineRepositoryImpl
import com.melzzz.vaxsafe_project1.features.vaccines.domain.repository.VaccineRepository

class AppContainer(context: Context) {

    val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    val vaccineRepository: VaccineRepository by lazy {
        VaccineRepositoryImpl(firestore)
    }
}