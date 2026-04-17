package com.melzzz.vaxsafe_project1.features.vaccines.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.melzzz.vaxsafe_project1.core.ui.theme.MedicalPrimary
import com.melzzz.vaxsafe_project1.features.vaccines.domain.model.VaccineStatus
import com.melzzz.vaxsafe_project1.features.vaccines.presentation.components.AddVaccineDialog
import com.melzzz.vaxsafe_project1.features.vaccines.presentation.components.MapBottomSheet
import com.melzzz.vaxsafe_project1.features.vaccines.presentation.components.UserProfileHeader
import com.melzzz.vaxsafe_project1.features.vaccines.presentation.components.VaccineCard
import com.melzzz.vaxsafe_project1.features.vaccines.presentation.viewmodels.DashboardViewModel
import com.melzzz.vaxsafe_project1.features.vaccines.presentation.viewmodels.DashboardViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    factory: DashboardViewModelFactory,
    onLogout: () -> Unit,           // ← parámetro nuevo
    modifier: Modifier = Modifier
) {
    val viewModel: DashboardViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "VAXSAFE",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Black,
                            letterSpacing = 2.sp
                        )
                    )
                },
                actions = {                          // ← dentro del CenterAlignedTopAppBar
                    IconButton(onClick = onLogout) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                            contentDescription = "Cerrar sesión"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = MedicalPrimary
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.toggleAddDialog() },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Agregar vacuna"
                )
            }
        },
        modifier = modifier
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            UserProfileHeader(
                name = uiState.userProfile.name,
                bloodType = uiState.userProfile.bloodType
            )

            Spacer(modifier = Modifier.height(8.dp))

            when {
                uiState.isLoading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                uiState.error != null -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "⚠️ Error",
                                style = MaterialTheme.typography.titleLarge,
                                color = MaterialTheme.colorScheme.error
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = uiState.error ?: "Error desconocido",
                                style = MaterialTheme.typography.bodyMedium
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = { viewModel.loadVaccines() }) {
                                Text("Reintentar")
                            }
                        }
                    }
                }

                uiState.vaccines.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "💉",
                                style = MaterialTheme.typography.displayLarge
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "No hay vacunas registradas",
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Toca el botón + para agregar tu primera vacuna",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }

                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.vaccines) { vaccine ->
                            VaccineCard(
                                vaccine = vaccine,
                                onLocationClick = { viewModel.toggleMapSheet() },
                                onStatusToggle = {
                                    val newStatus = if (vaccine.status == VaccineStatus.APPLIED)
                                        VaccineStatus.PENDING else VaccineStatus.APPLIED
                                    viewModel.updateVaccineStatus(vaccine.id, newStatus)
                                },
                                onDelete = { viewModel.deleteVaccine(vaccine.id) }
                            )
                        }
                    }
                }
            }
        }

        if (uiState.showAddDialog) {
            AddVaccineDialog(
                onDismiss = { viewModel.toggleAddDialog() },
                onConfirm = { vaccine -> viewModel.addVaccine(vaccine) }
            )
        }

        if (uiState.showMapSheet) {
            MapBottomSheet(onDismiss = { viewModel.toggleMapSheet() })
        }
    }
}