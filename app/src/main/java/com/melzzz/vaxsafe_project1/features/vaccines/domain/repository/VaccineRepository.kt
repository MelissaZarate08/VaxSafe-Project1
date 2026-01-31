package com.melzzz.vaxsafe_project1.features.vaccines.domain.repository

import com.melzzz.vaxsafe_project1.features.vaccines.domain.model.Vaccine
import com.melzzz.vaxsafe_project1.features.vaccines.domain.model.VaccineStatus

interface VaccineRepository {
    suspend fun getVaccines(): List<Vaccine>
    suspend fun addVaccine(vaccine: Vaccine): Result<Unit>
    suspend fun updateVaccineStatus(vaccineId: String, newStatus: VaccineStatus): Result<Unit>
    suspend fun deleteVaccine(vaccineId: String): Result<Unit>
}