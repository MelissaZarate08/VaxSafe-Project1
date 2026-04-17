package com.melzzz.vaxsafe_project1.features.vaccines.presentation.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.melzzz.vaxsafe_project1.core.ui.theme.*
import com.melzzz.vaxsafe_project1.features.vaccines.domain.model.*

@Composable
fun VaccineCard(
    vaccine: Vaccine,
    onLocationClick: () -> Unit,
    onStatusToggle: () -> Unit,
    onDelete: () -> Unit,
    modifier: Modifier = Modifier // 1. AGREGAMOS EL PARÁMETRO AQUÍ
) {
    val isApplied = vaccine.status == VaccineStatus.APPLIED

    val statusColor by animateColorAsState(
        targetValue = if (isApplied) SuccessGreen else WarningAmber,
        animationSpec = tween(400),
        label = "status_color"
    )

    Box(
        // 2. USAMOS EL MODIFIER AQUÍ (nota que empieza con minúscula)
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(
                Brush.linearGradient(
                    listOf(
                        Color(0x1AFFFFFF),
                        Color(0x0DFFFFFF)
                    )
                )
            )
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    listOf(
                        Color(0x33FFFFFF),
                        if (isApplied) SuccessGreen.copy(alpha = 0.3f)
                        else WarningAmber.copy(alpha = 0.2f)
                    )
                ),
                shape = RoundedCornerShape(20.dp)
            )
    ) {
        // Barra lateral de estado
        Box(
            modifier = Modifier
                .align(Alignment.CenterStart)
                .width(4.dp)
                .fillMaxHeight()
                .background(
                    Brush.verticalGradient(
                        listOf(statusColor, statusColor.copy(alpha = 0.4f))
                    ),
                    RoundedCornerShape(topStart = 20.dp, bottomStart = 20.dp)
                )
        )

        // Brillo decorativo en esquina superior derecha
        Canvas(modifier = Modifier.matchParentSize()) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF8B7CF6).copy(alpha = 0.08f),
                        Color.Transparent
                    ),
                    center = Offset(size.width * 0.9f, size.height * 0.1f),
                    radius = size.width * 0.35f
                ),
                center = Offset(size.width * 0.9f, size.height * 0.1f),
                radius = size.width * 0.35f
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, top = 14.dp, end = 8.dp, bottom = 14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Icono de vacuna
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        if (isApplied)
                            SuccessGreen.copy(alpha = 0.15f)
                        else
                            WarningAmber.copy(alpha = 0.12f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (isApplied) "💉" else "⏳",
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = vaccine.name,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 15.sp,
                    color = TextOnDark,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = vaccine.laboratory,
                    fontSize = 12.sp,
                    color = TextMuted,
                    maxLines = 1
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Badge fecha
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = LilacPrimary.copy(alpha = 0.15f)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Outlined.CalendarToday,
                                null,
                                tint = LilacLight,
                                modifier = Modifier.size(10.dp)
                            )
                            Spacer(modifier = Modifier.width(3.dp))
                            Text(
                                vaccine.date,
                                fontSize = 10.sp,
                                color = LilacLight,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }

                    // Badge estado
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = statusColor.copy(alpha = 0.15f)
                    ) {
                        Text(
                            text = if (isApplied) "Aplicada" else "Pendiente",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            fontSize = 10.sp,
                            color = statusColor,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }

            // Acciones
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                if (!isApplied) {
                    IconButton(
                        onClick = onLocationClick,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            Icons.Filled.LocationOn,
                            "Ubicación",
                            tint = SkyBlue,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }

                IconButton(
                    onClick = onStatusToggle,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector = if (isApplied)
                            Icons.Filled.CheckCircle
                        else
                            Icons.Outlined.RadioButtonUnchecked,
                        contentDescription = "Cambiar estado",
                        tint = statusColor,
                        modifier = Modifier.size(20.dp)
                    )
                }

                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        Icons.Outlined.Delete,
                        "Eliminar",
                        tint = ErrorCoral.copy(alpha = 0.7f),
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}