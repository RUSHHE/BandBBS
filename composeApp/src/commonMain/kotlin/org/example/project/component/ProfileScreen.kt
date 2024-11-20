package org.example.project.component

import androidx.compose.runtime.Composable
import org.example.project.viewmodel.ProfileViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ProfileScreen() {
    val viewModel = koinViewModel<ProfileViewModel>()
    LoginPage()
}

@Composable
expect fun LoginPage()