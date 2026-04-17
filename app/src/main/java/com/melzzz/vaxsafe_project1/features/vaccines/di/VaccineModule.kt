package com.melzzz.vaxsafe_project1.features.vaccines.di

import com.melzzz.vaxsafe_project1.core.di.AppContainer
import com.melzzz.vaxsafe_project1.features.vaccines.domain.usecase.AddVaccineUseCase
import com.melzzz.vaxsafe_project1.features.vaccines.domain.usecase.DeleteVaccineUseCase
import com.melzzz.vaxsafe_project1.features.vaccines.domain.usecase.GetVaccinesUseCase
import com.melzzz.vaxsafe_project1.features.vaccines.domain.usecase.UpdateVaccineStatusUseCase
import com.melzzz.vaxsafe_project1.features.vaccines.presentation.viewmodels.DashboardViewModelFactory

class VaccineModule(private val appContainer: AppContainer) {

    private fun provideGetVaccinesUseCase(): GetVaccinesUseCase {
        return GetVaccinesUseCase(appContainer.vaccineRepository)
    }

    private fun provideAddVaccineUseCase(): AddVaccineUseCase {
        return AddVaccineUseCase(appContainer.vaccineRepository)
    }

    private fun provideUpdateVaccineStatusUseCase(): UpdateVaccineStatusUseCase {
        return UpdateVaccineStatusUseCase(appContainer.vaccineRepository)
    }

    private fun provideDeleteVaccineUseCase(): DeleteVaccineUseCase {
        return DeleteVaccineUseCase(appContainer.vaccineRepository)
    }

    // Agrega el authRepository a la factoría
    fun provideDashboardViewModelFactory(): DashboardViewModelFactory {
        return DashboardViewModelFactory(
            getVaccinesUseCase = provideGetVaccinesUseCase(),
            addVaccineUseCase = provideAddVaccineUseCase(),
            updateVaccineStatusUseCase = provideUpdateVaccineStatusUseCase(),
            deleteVaccineUseCase = provideDeleteVaccineUseCase(),
            authRepository = appContainer.authRepository // <-- NUEVO LÍNEA
        )
    }
}