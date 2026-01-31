package com.melzzz.vaxsafe_project1.features.vaccines.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.melzzz.vaxsafe_project1.core.util.Constants
import com.melzzz.vaxsafe_project1.features.vaccines.data.mapper.toDomain
import com.melzzz.vaxsafe_project1.features.vaccines.data.mapper.toDto
import com.melzzz.vaxsafe_project1.features.vaccines.data.model.VaccineDto
import com.melzzz.vaxsafe_project1.features.vaccines.domain.model.Vaccine
import com.melzzz.vaxsafe_project1.features.vaccines.domain.model.VaccineStatus
import com.melzzz.vaxsafe_project1.features.vaccines.domain.repository.VaccineRepository
import kotlinx.coroutines.tasks.await

class VaccineRepositoryImpl(
    private val firestore: FirebaseFirestore
) : VaccineRepository {

    private val collection = firestore.collection(Constants.COLLECTION_VACCINES)

    override suspend fun getVaccines(): List<Vaccine> {
        return try {
            val snapshot = collection.get().await()
            snapshot.documents.mapNotNull { doc ->
                doc.toObject(VaccineDto::class.java)?.copy(id = doc.id)?.toDomain()
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun addVaccine(vaccine: Vaccine): Result<Unit> {
        return try {
            collection.add(vaccine.toDto()).await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateVaccineStatus(
        vaccineId: String,
        newStatus: VaccineStatus
    ): Result<Unit> {
        return try {
            collection.document(vaccineId)
                .update("status", newStatus.name)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteVaccine(vaccineId: String): Result<Unit> {
        return try {
            collection.document(vaccineId).delete().await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}