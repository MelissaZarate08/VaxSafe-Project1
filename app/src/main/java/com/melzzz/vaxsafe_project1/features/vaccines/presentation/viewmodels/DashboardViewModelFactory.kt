package com.melzzz.vaxsafe_project1.features.vaccines.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.melzzz.vaxsafe_project1.features.vaccines.domain.usecase.AddVaccineUseCase
import com.melzzz.vaxsafe_project1.features.vaccines.domain.usecase.DeleteVaccineUseCase
import com.melzzz.vaxsafe_project1.features.vaccines.domain.usecase.GetVaccinesUseCase
import com.melzzz.vaxsafe_project1.features.vaccines.domain.usecase.UpdateVaccineStatusUseCase

class DashboardViewModelFactory(
    private val getVaccinesUseCase: GetVaccinesUseCase,
    private val addVaccineUseCase: AddVaccineUseCase,
    private val updateVaccineStatusUseCase: UpdateVaccineStatusUseCase,
    private val deleteVaccineUseCase: DeleteVaccineUseCase
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DashboardViewModel::class.java)) {
            return DashboardViewModel(
                getVaccinesUseCase = getVaccinesUseCase,
                addVaccineUseCase = addVaccineUseCase,
                updateVaccineStatusUseCase = updateVaccineStatusUseCase,
                deleteVaccineUseCase = deleteVaccineUseCase
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}