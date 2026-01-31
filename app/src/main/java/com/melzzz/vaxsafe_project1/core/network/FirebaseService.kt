package com.melzzz.vaxsafe_project1.core.network

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

object FirebaseService {
    val db: FirebaseFirestore by lazy {
        Firebase.firestore
    }
}