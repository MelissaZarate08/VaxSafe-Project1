package com.melzzz.vaxsafe_project1.features.auth.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel

import com.melzzz.vaxsafe_project1.core.ui.theme.MedicalPrimary
import com.melzzz.vaxsafe_project1.core.ui.theme.MedicalSecondary
import com.melzzz.vaxsafe_project1.features.auth.domain.repository.AuthRepository
import com.melzzz.vaxsafe_project1.features.auth.presentation.viewmodels.AuthViewModel

@Composable
fun AuthScreen(
    authRepository: AuthRepository,
    onAuthenticated: () -> Unit
) {
    // 1. Instanciamos el ViewModel una sola vez aquí arriba
    val viewModel: AuthViewModel = viewModel(
        factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return AuthViewModel(authRepository) as T
            }
        }
    )

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // 2. Este es nuestro "Router". Decide qué pantalla completa mostrar.
    // Al cambiar entre ellas, sus variables internas (como el texto escrito) se limpian solas.
    if (uiState.isLoginMode) {
        LoginScreen(
            uiState = uiState,
            onLogin = { email, pass -> viewModel.login(email, pass) },
            onNavigateToRegister = { viewModel.toggleMode() },
            onAuthenticated = {
                viewModel.resetState() // Limpiamos caché antes de ir al Dashboard
                onAuthenticated()
            }
        )
    } else {
        RegisterScreen(
            uiState = uiState,
            onRegister = { email, pass, name, blood -> viewModel.register(email, pass, name, blood) }, // NUEVO
            onNavigateToLogin = { viewModel.toggleMode() }
        )
    }
}

// --- PANTALLA EXCLUSIVA DE INICIO DE SESIÓN ---
@OptIn(ExperimentalMaterial3Api::class)
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(Color(0xFF0B0E14), Color(0xFF1A1F26)))),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "💉 VAXSAFE",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontWeight = FontWeight.Black, letterSpacing = 4.sp, color = MedicalPrimary
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Inicia sesión en tu cuenta", color = Color.White.copy(alpha = 0.7f))
            Spacer(modifier = Modifier.height(40.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Si hay un error o un mensaje de éxito (como el de registro exitoso)
            uiState.error?.let {
                Spacer(modifier = Modifier.height(8.dp))
                val isSuccessMessage = it.contains("exitoso", ignoreCase = true)
                Text(
                    text = it,
                    color = if (isSuccessMessage) MedicalSecondary else MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = { onLogin(email, password) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading && email.isNotBlank() && password.isNotBlank()
            ) {
                Text(if (uiState.isLoading) "Cargando..." else "Entrar")
            }

            TextButton(onClick = onNavigateToRegister) {
                Text("¿No tienes cuenta? Regístrate", color = MedicalSecondary)
            }
        }
    }
}

// --- PANTALLA EXCLUSIVA DE REGISTRO ---
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    uiState: AuthUiState,
    onRegister: (String, String, String, String) -> Unit, // NUEVO
    onNavigateToLogin: () -> Unit
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") } // NUEVO
    var bloodType by remember { mutableStateOf("") } // NUEVO

    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxSize().background(Brush.verticalGradient(listOf(Color(0xFF0B0E14), Color(0xFF1A1F26)))),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("💉 VAXSAFE", style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Black, letterSpacing = 4.sp, color = MedicalPrimary))
            Spacer(modifier = Modifier.height(8.dp))
            Text("Crea una cuenta nueva", color = Color.White.copy(alpha = 0.7f))
            Spacer(modifier = Modifier.height(24.dp))

            // NUEVOS CAMPOS
            OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Nombre completo") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(value = bloodType, onValueChange = { bloodType = it }, label = { Text("Tipo de sangre (Ej: O+)") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            Spacer(modifier = Modifier.height(8.dp))

            // CAMPOS ANTERIORES
            OutlinedTextField(value = email, onValueChange = { email = it }, label = { Text("Correo") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password, onValueChange = { password = it }, label = { Text("Contraseña") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = { IconButton(onClick = { passwordVisible = !passwordVisible }) { Icon(if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff, null) } },
                modifier = Modifier.fillMaxWidth(), singleLine = true
            )
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = confirmPassword, onValueChange = { confirmPassword = it }, label = { Text("Confirmar Contraseña") },
                visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = { IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) { Icon(if (confirmPasswordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff, null) } },
                modifier = Modifier.fillMaxWidth(), singleLine = true,
                isError = password.isNotEmpty() && confirmPassword.isNotEmpty() && password != confirmPassword
            )

            uiState.error?.let { Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall) }

            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onRegister(email, password, name, bloodType) },
                modifier = Modifier.fillMaxWidth(),
                // Validamos que todo esté lleno
                enabled = !uiState.isLoading && email.isNotBlank() && password.isNotBlank() && name.isNotBlank() && bloodType.isNotBlank() && password == confirmPassword
            ) {
                Text(if (uiState.isLoading) "Registrando..." else "Registrarse")
            }

            TextButton(onClick = onNavigateToLogin) {
                Text("¿Ya tienes cuenta? Inicia sesión", color = MedicalSecondary)
            }
        }
    }
}