package com.melzzz.vaxsafe_project1.features.auth.presentation.screens

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.*
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.*
import androidx.compose.ui.unit.*
import androidx.lifecycle.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.melzzz.vaxsafe_project1.core.ui.theme.*
import com.melzzz.vaxsafe_project1.features.auth.domain.repository.AuthRepository
import com.melzzz.vaxsafe_project1.features.auth.presentation.viewmodels.AuthViewModel

/* ─────────────────────────────────────────────
   ROUTER PRINCIPAL
   ───────────────────────────────────────────── */
@Composable
fun AuthScreen(
    authRepository: AuthRepository,
    onAuthenticated: () -> Unit
) {
    val viewModel: AuthViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return AuthViewModel(authRepository) as T
            }
        }
    )
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    AnimatedContent(
        targetState = uiState.isLoginMode,
        transitionSpec = {
            if (targetState) {
                slideInHorizontally { -it } + fadeIn() togetherWith
                        slideOutHorizontally { it } + fadeOut()
            } else {
                slideInHorizontally { it } + fadeIn() togetherWith
                        slideOutHorizontally { -it } + fadeOut()
            }
        },
        label = "auth_transition"
    ) { isLogin ->
        if (isLogin) {
            LoginScreen(
                uiState = uiState,
                onLogin = { email, pass -> viewModel.login(email, pass) },
                onNavigateToRegister = { viewModel.toggleMode() },
                onAuthenticated = {
                    viewModel.resetState()
                    onAuthenticated()
                }
            )
        } else {
            RegisterScreen(
                uiState = uiState,
                onRegister = { email, pass, name, blood ->
                    viewModel.register(email, pass, name, blood)
                },
                onNavigateToLogin = { viewModel.toggleMode() }
            )
        }
    }
}

/* ─────────────────────────────────────────────
   FONDO COMPARTIDO CON ORBES ANIMADOS
   ───────────────────────────────────────────── */
@Composable
private fun MedicalBackground(content: @Composable BoxScope.() -> Unit) {
    val infiniteAnim = rememberInfiniteTransition(label = "bg_orbs")
    val orb1 by infiniteAnim.animateFloat(
        initialValue = 0f, targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(6000, easing = LinearEasing), RepeatMode.Reverse
        ), label = "orb1"
    )
    val orb2 by infiniteAnim.animateFloat(
        initialValue = 1f, targetValue = 0f,
        animationSpec = infiniteRepeatable(
            tween(8000, easing = LinearEasing), RepeatMode.Reverse
        ), label = "orb2"
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(Color(0xFF0F0E2A), Color(0xFF1A1040), Color(0xFF0C1A3E))
                )
            )
    ) {
        // Orbe lila grande
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF8B7CF6).copy(alpha = 0.25f + orb1 * 0.12f),
                        Color.Transparent
                    ),
                    center = Offset(size.width * 0.15f, size.height * 0.15f),
                    radius = size.width * 0.55f
                ),
                center = Offset(size.width * 0.15f, size.height * 0.15f),
                radius = size.width * 0.55f
            )
            // Orbe celeste
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF48C7EA).copy(alpha = 0.20f + orb2 * 0.10f),
                        Color.Transparent
                    ),
                    center = Offset(size.width * 0.85f, size.height * 0.75f),
                    radius = size.width * 0.5f
                ),
                center = Offset(size.width * 0.85f, size.height * 0.75f),
                radius = size.width * 0.5f
            )
            // Orbe pequeño intermedio
            drawCircle(
                brush = Brush.radialGradient(
                    colors = listOf(
                        Color(0xFF6C5CE7).copy(alpha = 0.15f),
                        Color.Transparent
                    ),
                    center = Offset(size.width * 0.7f, size.height * 0.25f),
                    radius = size.width * 0.3f
                ),
                center = Offset(size.width * 0.7f, size.height * 0.25f),
                radius = size.width * 0.3f
            )
        }
        content()
    }
}

/* ─────────────────────────────────────────────
   LOGO + HERO CARD SUPERIOR
   ───────────────────────────────────────────── */
@Composable
private fun LogoHero(subtitle: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        // Icono circular con gradiente
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(
                    Brush.linearGradient(
                        listOf(LilacPrimary, SkyBlue),
                        start = Offset(0f, 0f),
                        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            // Cruz médica
            Box(
                modifier = Modifier
                    .size(36.dp, 12.dp)
                    .background(Color.White, RoundedCornerShape(4.dp))
            )
            Box(
                modifier = Modifier
                    .size(12.dp, 36.dp)
                    .background(Color.White, RoundedCornerShape(4.dp))
            )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
            text = "VaxSafe",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            // LA SOLUCIÓN: Metemos el brush dentro de un TextStyle
            style = TextStyle(
                brush = Brush.linearGradient(listOf(LilacLight, SkyBlueLight))
            ),
            letterSpacing = 1.sp
        )

        Text(
            text = subtitle,
            fontSize = 14.sp,
            color = TextMuted,
            letterSpacing = 0.3.sp
        )
    }
}

/* ─────────────────────────────────────────────
   CAMPO DE TEXTO GLASSMORPHISM
   ───────────────────────────────────────────── */
@Composable
private fun GlassTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String = "",
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardType: KeyboardType = KeyboardType.Text,
    isError: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontSize = 13.sp) },
        placeholder = { Text(placeholder, color = TextMuted.copy(alpha = 0.6f), fontSize = 13.sp) },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation,
        keyboardOptions = androidx.compose.foundation.text.KeyboardOptions(keyboardType = keyboardType),
        isError = isError,
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor       = TextOnDark,
            unfocusedTextColor     = TextOnDark,
            focusedContainerColor  = GlassWhite,
            unfocusedContainerColor= GlassOverlay,
            focusedBorderColor     = LilacPrimary,
            unfocusedBorderColor   = GlassBorder,
            focusedLabelColor      = LilacLight,
            unfocusedLabelColor    = TextMuted,
            cursorColor            = LilacLight,
            errorBorderColor       = ErrorCoral,
            errorLabelColor        = ErrorCoral,
            errorLeadingIconColor  = ErrorCoral
        )
    )
}

/* ─────────────────────────────────────────────
   BOTÓN PRIMARIO GRADIENTE
   ───────────────────────────────────────────── */
@Composable
private fun GradientButton(
    text: String,
    onClick: () -> Unit,
    enabled: Boolean = true,
    isLoading: Boolean = false
) {
    val alpha by animateFloatAsState(
        targetValue = if (enabled) 1f else 0.5f, label = "btn_alpha"
    )
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(54.dp)
            .clip(RoundedCornerShape(16.dp))
            .alpha(alpha)
            .background(
                if (enabled)
                    Brush.linearGradient(listOf(LilacDark, LilacPrimary, SkyBlue))
                else
                    Brush.linearGradient(listOf(TextMuted, TextMuted))
            )
            .clickable(enabled = enabled && !isLoading) { onClick() },
        contentAlignment = Alignment.Center
    ) {
        if (isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = Color.White,
                strokeWidth = 2.dp
            )
        } else {
            Text(
                text = text,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                letterSpacing = 0.5.sp
            )
        }
    }
}

/* ─────────────────────────────────────────────
   CARD GLASS CONTENEDORA
   ───────────────────────────────────────────── */
@Composable
private fun GlassCard(content: @Composable ColumnScope.() -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(28.dp))
            .background(Color(0x1AFFFFFF))
            .border(
                width = 1.dp,
                brush = Brush.linearGradient(
                    listOf(
                        Color(0x55FFFFFF),
                        Color(0x11FFFFFF),
                        Color(0x33A78BFA)
                    )
                ),
                shape = RoundedCornerShape(28.dp)
            )
    ) {
        Column(
            modifier = Modifier.padding(28.dp),
            content = content
        )
    }
}

/* ─────────────────────────────────────────────
   PANTALLA DE LOGIN
   ───────────────────────────────────────────── */
@Composable
fun LoginScreen(
    uiState: AuthUiState,
    onLogin: (String, String) -> Unit,
    onNavigateToRegister: () -> Unit,
    onAuthenticated: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    LaunchedEffect(uiState.isAuthenticated) {
        if (uiState.isAuthenticated) onAuthenticated()
    }

    MedicalBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(80.dp))

            LogoHero(subtitle = "Tu historial de vacunas, seguro y moderno")

            Spacer(modifier = Modifier.height(40.dp))

            GlassCard {
                Text(
                    text = "Iniciar sesión",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextOnDark
                )
                Text(
                    text = "Bienvenido de vuelta",
                    fontSize = 13.sp,
                    color = TextMuted,
                    modifier = Modifier.padding(top = 2.dp, bottom = 24.dp)
                )

                GlassTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Correo electrónico",
                    placeholder = "doctor@vaxsafe.com",
                    leadingIcon = {
                        Icon(Icons.Outlined.Email, null,
                            tint = LilacLight, modifier = Modifier.size(20.dp))
                    },
                    keyboardType = KeyboardType.Email
                )

                Spacer(modifier = Modifier.height(14.dp))

                GlassTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Contraseña",
                    placeholder = "••••••••",
                    leadingIcon = {
                        Icon(Icons.Outlined.Lock, null,
                            tint = LilacLight, modifier = Modifier.size(20.dp))
                    },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                imageVector = if (passwordVisible)
                                    Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                contentDescription = null,
                                tint = TextMuted,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible)
                        VisualTransformation.None else PasswordVisualTransformation()
                )

                // Mensaje de error / éxito
                AnimatedVisibility(visible = uiState.error != null) {
                    val isSuccess = uiState.error?.contains("exitoso", ignoreCase = true) == true
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp),
                        shape = RoundedCornerShape(10.dp),
                        color = if (isSuccess)
                            SuccessGreen.copy(alpha = 0.15f)
                        else
                            ErrorCoral.copy(alpha = 0.15f)
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = if (isSuccess)
                                    Icons.Filled.CheckCircle else Icons.Filled.Warning,
                                contentDescription = null,
                                tint = if (isSuccess) SuccessGreen else ErrorCoral,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = uiState.error ?: "",
                                fontSize = 12.sp,
                                color = if (isSuccess) SuccessGreen else ErrorCoral
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                GradientButton(
                    text = "Entrar",
                    onClick = { onLogin(email, password) },
                    enabled = email.isNotBlank() && password.isNotBlank(),
                    isLoading = uiState.isLoading
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Divider "o"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = GlassBorder
                    )
                    Text(
                        "  o  ",
                        color = TextMuted,
                        fontSize = 12.sp
                    )
                    HorizontalDivider(
                        modifier = Modifier.weight(1f),
                        color = GlassBorder
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedButton(
                    onClick = onNavigateToRegister,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(14.dp),
                    border = BorderStroke(1.dp, Brush.linearGradient(listOf(LilacPrimary, SkyBlue))),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = LilacLight
                    )
                ) {
                    Text(
                        "Crear nueva cuenta",
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Chips de info médica
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("🔒 Seguro", "💉 Preciso", "⚡ Rápido").forEach { badge ->
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = GlassWhite,
                        border = BorderStroke(0.5.dp, GlassBorder)
                    ) {
                        Text(
                            text = badge,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            fontSize = 11.sp,
                            color = TextMuted
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}

/* ─────────────────────────────────────────────
   PANTALLA DE REGISTRO
   ───────────────────────────────────────────── */
@Composable
fun RegisterScreen(
    uiState: AuthUiState,
    onRegister: (String, String, String, String) -> Unit,
    onNavigateToLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var bloodType by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmVisible by remember { mutableStateOf(false) }

    val passwordsMatch = password.isEmpty() || confirmPassword.isEmpty() || password == confirmPassword

    MedicalBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(60.dp))

            LogoHero(subtitle = "Crea tu perfil médico personal")

            Spacer(modifier = Modifier.height(32.dp))

            GlassCard {
                Text(
                    "Crear cuenta",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextOnDark
                )
                Text(
                    "Regístrate en segundos",
                    fontSize = 13.sp,
                    color = TextMuted,
                    modifier = Modifier.padding(top = 2.dp, bottom = 20.dp)
                )

                // Fila: Nombre + Tipo sangre
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Box(modifier = Modifier.weight(1.7f)) {
                        GlassTextField(
                            value = name,
                            onValueChange = { name = it },
                            label = "Nombre completo",
                            placeholder = "Dr. García",
                            leadingIcon = {
                                Icon(Icons.Outlined.Person, null,
                                    tint = LilacLight, modifier = Modifier.size(18.dp))
                            }
                        )
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        GlassTextField(
                            value = bloodType,
                            onValueChange = { bloodType = it.uppercase() },
                            label = "Sangre",
                            placeholder = "O+"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                GlassTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = "Correo electrónico",
                    placeholder = "correo@ejemplo.com",
                    leadingIcon = {
                        Icon(Icons.Outlined.Email, null,
                            tint = LilacLight, modifier = Modifier.size(18.dp))
                    },
                    keyboardType = KeyboardType.Email
                )

                Spacer(modifier = Modifier.height(12.dp))

                GlassTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = "Contraseña",
                    placeholder = "Mínimo 6 caracteres",
                    leadingIcon = {
                        Icon(Icons.Outlined.Lock, null,
                            tint = LilacLight, modifier = Modifier.size(18.dp))
                    },
                    trailingIcon = {
                        IconButton(onClick = { passwordVisible = !passwordVisible }) {
                            Icon(
                                if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                null, tint = TextMuted, modifier = Modifier.size(18.dp)
                            )
                        }
                    },
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation()
                )

                Spacer(modifier = Modifier.height(12.dp))

                GlassTextField(
                    value = confirmPassword,
                    onValueChange = { confirmPassword = it },
                    label = "Confirmar contraseña",
                    placeholder = "Repite tu contraseña",
                    leadingIcon = {
                        Icon(Icons.Outlined.LockOpen, null,
                            tint = if (passwordsMatch) LilacLight else ErrorCoral,
                            modifier = Modifier.size(18.dp))
                    },
                    trailingIcon = {
                        if (confirmPassword.isNotEmpty()) {
                            Icon(
                                imageVector = if (passwordsMatch) Icons.Filled.CheckCircle else Icons.Filled.Cancel,
                                contentDescription = null,
                                tint = if (passwordsMatch) SuccessGreen else ErrorCoral,
                                modifier = Modifier.size(18.dp).padding(end = 4.dp)
                            )
                        }
                        IconButton(onClick = { confirmVisible = !confirmVisible }) {
                            Icon(
                                if (confirmVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                                null, tint = TextMuted, modifier = Modifier.size(18.dp)
                            )
                        }
                    },
                    visualTransformation = if (confirmVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    isError = !passwordsMatch
                )

                // Error del servidor
                AnimatedVisibility(visible = uiState.error != null) {
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp),
                        shape = RoundedCornerShape(10.dp),
                        color = ErrorCoral.copy(alpha = 0.15f)
                    ) {
                        Row(
                            modifier = Modifier.padding(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Filled.Warning, null,
                                tint = ErrorCoral, modifier = Modifier.size(14.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(uiState.error ?: "", fontSize = 12.sp, color = ErrorCoral)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(22.dp))

                GradientButton(
                    text = "Crear cuenta",
                    onClick = { onRegister(email, password, name, bloodType) },
                    enabled = email.isNotBlank() && password.isNotBlank() &&
                            name.isNotBlank() && bloodType.isNotBlank() && passwordsMatch,
                    isLoading = uiState.isLoading
                )

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(
                    onClick = onNavigateToLogin,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("¿Ya tienes cuenta? ", color = TextMuted, fontSize = 13.sp)
                    Text(
                        "Inicia sesión",
                        color = LilacLight,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(40.dp))
        }
    }
}