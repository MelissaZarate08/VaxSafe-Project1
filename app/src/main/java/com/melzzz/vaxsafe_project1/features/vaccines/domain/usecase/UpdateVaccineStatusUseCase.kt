package com.melzzz.vaxsafe_project1.features.vaccines.domain.usecase

import com.melzzz.vaxsafe_project1.features.vaccines.domain.model.VaccineStatus
import com.melzzz.vaxsafe_project1.features.vaccines.domain.repository.VaccineRepository

class UpdateVaccineStatusUseCase(
    private val repository: VaccineRepository
) {
    suspend operator fun invoke(vaccineId: String, newStatus: VaccineStatus): Result<Unit> {
        return repository.updateVaccineStatus(vaccineId, newStatus)
    }
}