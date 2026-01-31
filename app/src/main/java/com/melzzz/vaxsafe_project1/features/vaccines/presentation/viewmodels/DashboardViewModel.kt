package com.melzzz.vaxsafe_project1.features.vaccines.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.melzzz.vaxsafe_project1.features.vaccines.domain.model.Vaccine
import com.melzzz.vaxsafe_project1.features.vaccines.domain.model.VaccineStatus
import com.melzzz.vaxsafe_project1.features.vaccines.domain.usecase.AddVaccineUseCase
import com.melzzz.vaxsafe_project1.features.vaccines.domain.usecase.DeleteVaccineUseCase
import com.melzzz.vaxsafe_project1.features.vaccines.domain.usecase.GetVaccinesUseCase
import com.melzzz.vaxsafe_project1.features.vaccines.domain.usecase.UpdateVaccineStatusUseCase
import com.melzzz.vaxsafe_project1.features.vaccines.presentation.screens.DashboardUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val getVaccinesUseCase: GetVaccinesUseCase,
    private val addVaccineUseCase: AddVaccineUseCase,
    private val updateVaccineStatusUseCase: UpdateVaccineStatusUseCase,
    private val deleteVaccineUseCase: DeleteVaccineUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadVaccines()
    }

    fun loadVaccines() {
        _uiState.update { it.copy(isLoading = true, error = null) }

        viewModelScope.launch {
            getVaccinesUseCase().fold(
                onSuccess = { vaccines ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            vaccines = vaccines,
                            error = null
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            error = error.message ?: "Error al cargar vacunas"
                        )
                    }
                }
            )
        }
    }

    fun addVaccine(vaccine: Vaccine) {
        viewModelScope.launch {
            addVaccineUseCase(vaccine).fold(
                onSuccess = {
                    _uiState.update { it.copy(showAddDialog = false) }
                    loadVaccines()
                },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(error = error.message ?: "Error al agregar vacuna")
                    }
                }
            )
        }
    }

    fun updateVaccineStatus(vaccineId: String, newStatus: VaccineStatus) {
        viewModelScope.launch {
            updateVaccineStatusUseCase(vaccineId, newStatus).fold(
                onSuccess = { loadVaccines() },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(error = error.message ?: "Error al actualizar estado")
                    }
                }
            )
        }
    }

    fun deleteVaccine(vaccineId: String) {
        viewModelScope.launch {
            deleteVaccineUseCase(vaccineId).fold(
                onSuccess = { loadVaccines() },
                onFailure = { error ->
                    _uiState.update {
                        it.copy(error = error.message ?: "Error al eliminar vacuna")
                    }
                }
            )
        }
    }

    fun toggleAddDialog() {
        _uiState.update { it.copy(showAddDialog = !it.showAddDialog) }
    }

    fun toggleMapSheet() {
        _uiState.update { it.copy(showMapSheet = !it.showMapSheet) }
    }
}