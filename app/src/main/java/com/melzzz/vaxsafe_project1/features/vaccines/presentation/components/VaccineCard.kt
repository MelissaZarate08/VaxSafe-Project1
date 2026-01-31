package com.melzzz.vaxsafe_project1.features.vaccines.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.melzzz.vaxsafe_project1.core.ui.theme.ErrorRed
import com.melzzz.vaxsafe_project1.core.ui.theme.MedicalSecondary
import com.melzzz.vaxsafe_project1.core.ui.theme.SuccessGreen
import com.melzzz.vaxsafe_project1.core.ui.theme.WarningOrange
import com.melzzz.vaxsafe_project1.features.vaccines.domain.model.Vaccine
import com.melzzz.vaxsafe_project1.features.vaccines.domain.model.VaccineStatus

@Composable
fun VaccineCard(
    vaccine: Vaccine,
    onLocationClick: () -> Unit,
    onStatusToggle: () -> Unit,
    onDelete: () -> Unit
) {
    val statusColor = if (vaccine.status == VaccineStatus.APPLIED) SuccessGreen else WarningOrange

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(6.dp)
                    .background(statusColor)
            )

            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = vaccine.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White
                    )
                    Text(
                        text = vaccine.laboratory,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.DateRange,
                            contentDescription = null,
                            modifier = Modifier.size(14.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = vaccine.date,
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (vaccine.status == VaccineStatus.PENDING) {
                        IconButton(onClick = onLocationClick) {
                            Icon(Icons.Default.LocationOn, "Mapa", tint = MedicalSecondary)
                        }
                    }

                    IconButton(onClick = onStatusToggle) {
                        Icon(
                            imageVector = if (vaccine.status == VaccineStatus.APPLIED)
                                Icons.Default.CheckCircle else Icons.Default.RadioButtonUnchecked,
                            contentDescription = "Estado",
                            tint = statusColor
                        )
                    }

                    IconButton(onClick = onDelete) {
                        Icon(Icons.Default.Delete, "Borrar", tint = ErrorRed.copy(alpha = 0.7f))
                    }
                }
            }
        }
    }
}