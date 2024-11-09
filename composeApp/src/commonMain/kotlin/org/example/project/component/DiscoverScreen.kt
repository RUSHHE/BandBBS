package org.example.project.component

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.example.project.viewmodel.DiscoverViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun DiscoverScreen() {
    val viewModel = koinViewModel<DiscoverViewModel>()
    // 这里可以显示 Discover 页面的内容
    Text(text = "Discover Page", modifier = Modifier.padding(16.dp))
}