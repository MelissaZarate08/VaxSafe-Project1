package com.melzzz.vaxsafe_project1.features.vaccines.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.melzzz.vaxsafe_project1.core.util.DateUtils
import com.melzzz.vaxsafe_project1.features.vaccines.domain.model.Vaccine
import com.melzzz.vaxsafe_project1.features.vaccines.domain.model.VaccineStatus

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
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp)
            ) {
                Text(
                    text = "Agregar Vacuna",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Nombre de la vacuna") },
                    placeholder = { Text("Ej: COVID-19") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = laboratory,
                    onValueChange = { laboratory = it },
                    label = { Text("Laboratorio") },
                    placeholder = { Text("Ej: Pfizer") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = date,
                    onValueChange = { date = it },
                    label = { Text("Fecha (dd/MM/yyyy)") },
                    placeholder = { Text("01/01/2026") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Estado:",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterChip(
                        selected = selectedStatus == VaccineStatus.APPLIED,
                        onClick = { selectedStatus = VaccineStatus.APPLIED },
                        label = { Text("Aplicada") },
                        modifier = Modifier.weight(1f)
                    )
                    FilterChip(
                        selected = selectedStatus == VaccineStatus.PENDING,
                        onClick = { selectedStatus = VaccineStatus.PENDING },
                        label = { Text("Pendiente") },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancelar")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (name.isNotBlank() && laboratory.isNotBlank()) {
                                onConfirm(
                                    Vaccine(
                                        name = name,
                                        laboratory = laboratory,
                                        date = date,
                                        status = selectedStatus
                                    )
                                )
                            }
                        },
                        enabled = name.isNotBlank() && laboratory.isNotBlank()
                    ) {
                        Text("Guardar")
                    }
                }
            }
        }
    }
}