package com.melzzz.vaxsafe_project1.features.vaccines.domain.usecase

import com.melzzz.vaxsafe_project1.features.vaccines.domain.model.Vaccine
import com.melzzz.vaxsafe_project1.features.vaccines.domain.repository.VaccineRepository

class GetVaccinesUseCase(
    private val repository: VaccineRepository
) {
    suspend operator fun invoke(): Result<List<Vaccine>> {
        return try {
            val vaccines = repository.getVaccines()
            Result.success(vaccines)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}