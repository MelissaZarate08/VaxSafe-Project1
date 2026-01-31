package com.melzzz.vaxsafe_project1.features.vaccines.domain.usecase

import com.melzzz.vaxsafe_project1.features.vaccines.domain.repository.VaccineRepository

class DeleteVaccineUseCase(
    private val repository: VaccineRepository
) {
    suspend operator fun invoke(vaccineId: String): Result<Unit> {
        return repository.deleteVaccine(vaccineId)
    }
}