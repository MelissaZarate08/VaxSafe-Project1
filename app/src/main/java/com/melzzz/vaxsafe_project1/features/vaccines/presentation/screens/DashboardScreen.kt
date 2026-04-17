package com.melzzz.vaxsafe_project1.features.vaccines.presentation.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.TextAlign // <-- Agregado para el textAlign
import androidx.compose.ui.unit.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.melzzz.vaxsafe_project1.core.ui.theme.*
import com.melzzz.vaxsafe_project1.features.vaccines.domain.model.VaccineStatus
import com.melzzz.vaxsafe_project1.features.vaccines.presentation.components.*
import com.melzzz.vaxsafe_project1.features.vaccines.presentation.viewmodels.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    factory: DashboardViewModelFactory,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel: DashboardViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val appliedCount = uiState.vaccines.count { it.status == VaccineStatus.APPLIED }
    val pendingCount = uiState.vaccines.count { it.status == VaccineStatus.PENDING }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF0F0E2A), Color(0xFF1A1040), Color(0xFF0C1A3E))
                )
            )
    ) {
        // Orbe de fondo decorativo
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFF8B7CF6).copy(alpha = 0.12f), Color.Transparent),
                    center = Offset(size.width * 0.9f, size.height * 0.05f),
                    radius = size.width * 0.5f
                ),
                center = Offset(size.width * 0.9f, size.height * 0.05f),
                radius = size.width * 0.5f
            )
        }

        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                "Vax",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = TextOnDark
                            )
                            Text(
                                "Safe",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.ExtraBold,
                                // CORRECCIÓN 1: Brush dentro del TextStyle
                                style = androidx.compose.ui.text.TextStyle(
                                    brush = Brush.linearGradient(listOf(LilacLight, SkyBlueLight))
                                )
                            )
                        }
                    },
                    navigationIcon = {
                        Box(
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .size(36.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(GlassWhite)
                                .border(1.dp, GlassBorder, RoundedCornerShape(10.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("💉", fontSize = 16.sp)
                        }
                    },
                    actions = {
                        Box(
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .size(36.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(GlassWhite)
                                .border(1.dp, GlassBorder, RoundedCornerShape(10.dp))
                                .clickable { onLogout() },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.ExitToApp,
                                "Cerrar sesión",
                                tint = TextMuted,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = Color.Transparent
                    )
                )
            },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = { viewModel.toggleAddDialog() },
                    containerColor = Color.Transparent,
                    contentColor = Color.White,
                    elevation = FloatingActionButtonDefaults.elevation(0.dp),
                    modifier = Modifier
                        .size(60.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .background(
                            Brush.linearGradient(listOf(LilacDark, SkyBlue))
                        )
                ) {
                    Icon(
                        Icons.Filled.Add,
                        "Agregar",
                        modifier = Modifier.size(26.dp)
                    )
                }
            }
        ) { padding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                // Perfil de usuario
                item {
                    UserProfileHeader(
                        name = uiState.userProfile.name,
                        bloodType = uiState.userProfile.bloodType
                    )
                }

                // Stats cards
                item {
                    StatsRow(
                        total = uiState.vaccines.size,
                        applied = appliedCount,
                        pending = pendingCount
                    )
                }

                // Título sección vacunas
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            "Mis vacunas",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp,
                            color = TextOnDark,
                            modifier = Modifier.weight(1f)
                        )
                        if (uiState.vaccines.isNotEmpty()) {
                            Surface(
                                shape = RoundedCornerShape(20.dp),
                                color = LilacPrimary.copy(alpha = 0.2f)
                            ) {
                                Text(
                                    "${uiState.vaccines.size} total",
                                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                                    fontSize = 12.sp,
                                    color = LilacLight
                                )
                            }
                        }
                    }
                }

                // Contenido principal
                when {
                    uiState.isLoading -> {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(200.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(
                                    color = LilacPrimary,
                                    strokeWidth = 3.dp
                                )
                            }
                        }
                    }

                    uiState.error != null -> {
                        item {
                            ErrorState(
                                message = uiState.error ?: "Error desconocido",
                                onRetry = { viewModel.loadVaccines() }
                            )
                        }
                    }

                    uiState.vaccines.isEmpty() -> {
                        item { EmptyState() }
                    }

                    else -> {
                        items(uiState.vaccines) { vaccine ->
                            VaccineCard(
                                vaccine = vaccine,
                                onLocationClick = { viewModel.toggleMapSheet() },
                                onStatusToggle = {
                                    val newStatus = if (vaccine.status == VaccineStatus.APPLIED)
                                        VaccineStatus.PENDING else VaccineStatus.APPLIED
                                    viewModel.updateVaccineStatus(vaccine.id, newStatus)
                                },
                                onDelete = { viewModel.deleteVaccine(vaccine.id) },
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
                            )
                        }
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

/* ─── Stats Row ─── */
@Composable
private fun StatsRow(total: Int, applied: Int, pending: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        StatCard(
            icon = "💉",
            label = "Total",
            value = "$total",
            gradient = listOf(LilacDark, LilacPrimary),
            modifier = Modifier.weight(1f)
        )
        StatCard(
            icon = "✅",
            label = "Aplicadas",
            value = "$applied",
            gradient = listOf(Color(0xFF16A34A), SuccessGreen),
            modifier = Modifier.weight(1f)
        )
        StatCard(
            icon = "⏳",
            label = "Pendientes",
            value = "$pending",
            gradient = listOf(Color(0xFFD97706), WarningAmber),
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun StatCard(
    icon: String,
    label: String,
    value: String,
    gradient: List<Color>,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0x1AFFFFFF))
            .border(1.dp, GlassBorder, RoundedCornerShape(16.dp))
            .padding(12.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(icon, fontSize = 20.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                value,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 22.sp,
                // CORRECCIÓN 2: Brush dentro del TextStyle
                style = androidx.compose.ui.text.TextStyle(
                    brush = Brush.linearGradient(gradient)
                )
            )
            Text(
                label,
                fontSize = 11.sp,
                color = TextMuted
            )
        }
    }
}

/* ─── Empty State ─── */
@Composable
private fun EmptyState() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(CircleShape)
                .background(GlassWhite)
                .border(1.dp, GlassBorder, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text("💉", fontSize = 40.sp)
        }
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            "Sin vacunas registradas",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp,
            color = TextOnDark
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            "Toca el botón + para agregar\ntu primera vacuna",
            fontSize = 14.sp,
            color = TextMuted,
            textAlign = TextAlign.Center
        )
    }
}

/* ─── Error State ─── */
@Composable
private fun ErrorState(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(40.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("⚠️", fontSize = 40.sp)
        Spacer(modifier = Modifier.height(12.dp))
        Text("Algo salió mal", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = ErrorCoral)
        Spacer(modifier = Modifier.height(6.dp))
        Text(message, fontSize = 13.sp, color = TextMuted,
            textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedButton(
            onClick = onRetry,
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(1.dp, ErrorCoral.copy(alpha = 0.5f)),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = ErrorCoral)
        ) {
            Text("Reintentar")
        }
    }
}