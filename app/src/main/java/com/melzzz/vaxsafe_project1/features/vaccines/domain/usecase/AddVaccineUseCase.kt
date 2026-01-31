package com.melzzz.vaxsafe_project1.features.vaccines.domain.usecase

import com.melzzz.vaxsafe_project1.features.vaccines.domain.model.Vaccine
import com.melzzz.vaxsafe_project1.features.vaccines.domain.repository.VaccineRepository

class AddVaccineUseCase(
    private val repository: VaccineRepository
) {
    suspend operator fun invoke(vaccine: Vaccine): Result<Unit> {
        return repository.addVaccine(vaccine)
    }
}