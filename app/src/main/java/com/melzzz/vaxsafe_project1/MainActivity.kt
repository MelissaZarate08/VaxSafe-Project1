package com.melzzz.vaxsafe_project1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.melzzz.vaxsafe_project1.core.di.AppContainer
import com.melzzz.vaxsafe_project1.features.vaccines.di.VaccineModule
import com.melzzz.vaxsafe_project1.features.vaccines.presentation.screens.DashboardScreen
import com.melzzz.vaxsafe_project1.core.ui.theme.VaxSafeProject1Theme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.melzzz.vaxsafe_project1.features.auth.presentation.screens.AuthScreen

class MainActivity : ComponentActivity() {

    private lateinit var appContainer: AppContainer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appContainer = AppContainer(this)

        val vaccineModule = VaccineModule(appContainer)
        val isLoggedIn = appContainer.authRepository.getCurrentUserId() != null

        enableEdgeToEdge()
        setContent {
            VaxSafeProject1Theme {
                CompositionLocalProvider(LocalLifecycleOwner provides this) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        var loggedIn by remember { mutableStateOf(isLoggedIn) }

                        if (loggedIn) {
                            DashboardScreen(
                                factory = vaccineModule.provideDashboardViewModelFactory(),
                                onLogout = {
                                    appContainer.authRepository.logout()
                                    loggedIn = false
                                }
                            )
                        } else {
                            AuthScreen(
                                authRepository = appContainer.authRepository,
                                onAuthenticated = { loggedIn = true }
                            )
                        }
                    }
                }
            }
        }
    }
}