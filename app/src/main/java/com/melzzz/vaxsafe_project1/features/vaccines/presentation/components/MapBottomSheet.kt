package com.melzzz.vaxsafe_project1.features.vaccines.presentation.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.melzzz.vaxsafe_project1.core.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapBottomSheet(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        modifier = modifier,
        containerColor = Color(0xFF151230),
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(top = 12.dp, bottom = 4.dp)
                    .size(width = 40.dp, height = 4.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(DividerColor)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp)
        ) {
            // Header
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                Brush.linearGradient(listOf(LilacPrimary, SkyBlue))
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            Icons.Filled.LocationOn,
                            null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            "Centros de Salud",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp,
                            color = TextOnDark
                        )
                        Text(
                            "Más cercanos a tu ubicación",
                            fontSize = 12.sp,
                            color = TextMuted
                        )
                    }
                    IconButton(onClick = onDismiss) {
                        Icon(Icons.Filled.Close, null, tint = TextMuted)
                    }
                }
            }

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 20.dp),
                color = DividerColor
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de centros
            val centers = listOf(
                Triple("Hospital General", "1.2 km", "Abierto 24 hrs"),
                Triple("Centro de Salud Norte", "2.5 km", "Cierra a las 8 PM"),
                Triple("Clínica Municipal", "3.8 km", "Abierto 24 hrs")
            )

            centers.forEach { (name, distance, status) ->
                HealthCenterItem(
                    name = name,
                    distance = distance,
                    status = status,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Banner próximamente
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(LilacPrimary.copy(alpha = 0.12f))
                    .border(1.dp, LilacPrimary.copy(alpha = 0.3f), RoundedCornerShape(14.dp))
                    .padding(14.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("🗺️", fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            "Integración con Maps",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp,
                            color = LilacLight
                        )
                        Text(
                            "Mapa interactivo próximamente disponible",
                            fontSize = 11.sp,
                            color = TextMuted
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun HealthCenterItem(
    name: String,
    distance: String,
    status: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0x1AFFFFFF))
            .border(1.dp, DividerColor, RoundedCornerShape(16.dp))
            .padding(14.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(SkyBlue.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text("🏥", fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = TextOnDark
                )
                Text(
                    status,
                    fontSize = 11.sp,
                    color = if (status.contains("24")) SuccessGreen else TextMuted
                )
            }

            Surface(
                shape = RoundedCornerShape(20.dp),
                color = LilacPrimary.copy(alpha = 0.15f)
            ) {
                Text(
                    text = distance,
                    modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = LilacLight
                )
            }
        }
    }
}