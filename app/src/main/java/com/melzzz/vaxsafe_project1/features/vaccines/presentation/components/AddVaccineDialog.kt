package com.melzzz.vaxsafe_project1.features.vaccines.presentation.components

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.melzzz.vaxsafe_project1.core.ui.theme.*
import com.melzzz.vaxsafe_project1.core.util.DateUtils
import com.melzzz.vaxsafe_project1.features.vaccines.domain.model.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddVaccineDialog(
    onDismiss: () -> Unit,
    onConfirm: (Vaccine) -> Unit,
    modifier: Modifier = Modifier
) {
    var name by remember { mutableStateOf("") }
    var laboratory by remember { mutableStateOf("") }
    var date by remember { mutableStateOf(DateUtils.getCurrentDate()) }
    var selectedStatus by remember { mutableStateOf(VaccineStatus.PENDING) }

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(28.dp))
                .background(
                    Brush.linearGradient(
                        listOf(Color(0xFF1C1A3E), Color(0xFF151230))
                    )
                )
                .border(
                    1.dp,
                    Brush.linearGradient(
                        listOf(
                            Color(0x55A78BFA),
                            Color(0x2248C7EA),
                            Color(0x11FFFFFF)
                        )
                    ),
                    RoundedCornerShape(28.dp)
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                Brush.linearGradient(listOf(LilacDark, SkyBlueDark))
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("💉", fontSize = 18.sp)
                    }
                    Spacer(modifier = Modifier.width(12.dp))
                    Column {
                        Text(
                            "Nueva vacuna",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 18.sp,
                            color = TextOnDark
                        )
                        Text(
                            "Completa los datos",
                            fontSize = 12.sp,
                            color = TextMuted
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
                HorizontalDivider(color = DividerColor)
                Spacer(modifier = Modifier.height(20.dp))

                // Campos
                DialogTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = "Nombre de la vacuna",
                    placeholder = "Ej: COVID-19, Influenza...",
                    icon = Icons.Outlined.MedicalServices
                )

                Spacer(modifier = Modifier.height(12.dp))

                DialogTextField(
                    value = laboratory,
                    onValueChange = { laboratory = it },
                    label = "Laboratorio / Fabricante",
                    placeholder = "Ej: Pfizer, AstraZeneca...",
                    icon = Icons.Outlined.Science
                )

                Spacer(modifier = Modifier.height(12.dp))

                DialogTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = "Fecha de aplicación",
                    placeholder = "dd/MM/yyyy",
                    icon = Icons.Outlined.CalendarToday
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Selector de estado
                Text(
                    "Estado de la vacuna",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextMuted
                )

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    StatusChip(
                        selected = selectedStatus == VaccineStatus.APPLIED,
                        label = "✅  Aplicada",
                        selectedColor = SuccessGreen,
                        onClick = { selectedStatus = VaccineStatus.APPLIED },
                        modifier = Modifier.weight(1f)
                    )
                    StatusChip(
                        selected = selectedStatus == VaccineStatus.PENDING,
                        label = "⏳  Pendiente",
                        selectedColor = WarningAmber,
                        onClick = { selectedStatus = VaccineStatus.PENDING },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botones
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = onDismiss,
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp),
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(1.dp, DividerColor),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = TextMuted
                        )
                    ) {
                        Text("Cancelar", fontSize = 14.sp)
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                if (name.isNotBlank() && laboratory.isNotBlank())
                                    Brush.linearGradient(listOf(LilacDark, SkyBlue))
                                else
                                    Brush.linearGradient(listOf(TextMuted, TextMuted))
                            )
                            .clickable(enabled = name.isNotBlank() && laboratory.isNotBlank()) {
                                onConfirm(
                                    Vaccine(
                                        name = name,
                                        laboratory = laboratory,
                                        date = date,
                                        status = selectedStatus
                                    )
                                )
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "Guardar",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun DialogTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontSize = 12.sp) },
        placeholder = { Text(placeholder, color = TextMuted.copy(alpha = 0.5f), fontSize = 13.sp) },
        leadingIcon = {
            Icon(icon, null, tint = LilacLight, modifier = Modifier.size(18.dp))
        },
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor        = TextOnDark,
            unfocusedTextColor      = TextOnDark,
            focusedContainerColor   = Color(0x1AFFFFFF),
            unfocusedContainerColor = Color(0x0DFFFFFF),
            focusedBorderColor      = LilacPrimary,
            unfocusedBorderColor    = DividerColor,
            focusedLabelColor       = LilacLight,
            unfocusedLabelColor     = TextMuted,
            cursorColor             = LilacLight
        )
    )
}

@Composable
private fun StatusChip(
    selected: Boolean,
    label: String,
    selectedColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(44.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(
                if (selected)
                    selectedColor.copy(alpha = 0.2f)
                else
                    Color(0x0DFFFFFF)
            )
            .border(
                width = 1.5.dp,
                color = if (selected) selectedColor.copy(alpha = 0.7f) else DividerColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontSize = 13.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            color = if (selected) selectedColor else TextMuted
        )
    }
}