package com.melzzz.vaxsafe_project1.features.vaccines.presentation.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.*
import com.melzzz.vaxsafe_project1.core.ui.theme.*

@Composable
fun UserProfileHeader(name: String, bloodType: String) {
    val infiniteAnim = rememberInfiniteTransition(label = "header_anim")
    val shimmer by infiniteAnim.animateFloat(
        initialValue = -1f, targetValue = 2f,
        animationSpec = infiniteRepeatable(tween(3000, easing = LinearEasing)),
        label = "shimmer"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Tarjeta principal con gradiente
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(
                    Brush.linearGradient(
                        listOf(
                            Color(0xFF6C5CE7),
                            Color(0xFF8B7CF6),
                            Color(0xFF48C7EA)
                        )
                    )
                )
        ) {
            // Patrón decorativo de fondo (círculos)
            Canvas(modifier = Modifier.matchParentSize()) {
                drawCircle(
                    color = Color.White.copy(alpha = 0.06f),
                    radius = size.height * 1.1f,
                    center = Offset(size.width * 1.1f, -size.height * 0.3f)
                )
                drawCircle(
                    color = Color.White.copy(alpha = 0.05f),
                    radius = size.height * 0.7f,
                    center = Offset(-size.width * 0.15f, size.height * 1.1f)
                )
                // Línea decorativa tipo ECG simplificada
                val path = androidx.compose.ui.graphics.Path().apply {
                    moveTo(0f, size.height * 0.65f)
                    lineTo(size.width * 0.1f, size.height * 0.65f)
                    lineTo(size.width * 0.13f, size.height * 0.45f)
                    lineTo(size.width * 0.16f, size.height * 0.85f)
                    lineTo(size.width * 0.19f, size.height * 0.30f)
                    lineTo(size.width * 0.22f, size.height * 0.65f)
                    lineTo(size.width * 0.35f, size.height * 0.65f)
                }
                drawPath(
                    path,
                    color = Color.White.copy(alpha = 0.15f),
                    style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2f)
                )
            }

            // Contenido
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar circular
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f))
                        .border(2.dp, Color.White.copy(alpha = 0.5f), CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    // Iniciales
                    val initials = name
                        .split(" ")
                        .take(2)
                        .mapNotNull { it.firstOrNull()?.uppercaseChar() }
                        .joinToString("")
                        .ifEmpty { "?" }

                    Text(
                        text = initials,
                        color = Color.White,
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 20.sp
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "Bienvenido/a 👋",
                        fontSize = 12.sp,
                        color = Color.White.copy(alpha = 0.75f),
                        letterSpacing = 0.3.sp
                    )
                    Text(
                        text = name.ifEmpty { "Mi perfil" },
                        fontSize = 20.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color.White,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    // Badge tipo sangre
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = Color.White.copy(alpha = 0.2f),
                        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.35f))
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("🩸", fontSize = 12.sp)
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Tipo ${bloodType.ifEmpty { "--" }}",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.White
                            )
                        }
                    }
                }

                // Badge médico
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Filled.HealthAndSafety,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Activo",
                        fontSize = 10.sp,
                        color = Color.White.copy(alpha = 0.7f)
                    )
                }
            }
        }
    }
}