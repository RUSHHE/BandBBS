package org.example.project.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.viewmodel.ResourceViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ResourceScreen() {
    val viewModel = koinViewModel<ResourceViewModel>()
    Text(text = "Resource Page", modifier = Modifier.padding(16.dp))
}