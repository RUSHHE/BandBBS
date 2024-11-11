package org.example.project.component

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.bandbbs.http.model.Block
import org.example.project.BlockItem
import org.example.project.Status
import org.example.project.viewmodel.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen() {
    val viewModel = koinViewModel<HomeViewModel>()
    val dataState by viewModel.dataState.collectAsState()

    when (dataState) {
        is Status.Loading -> {
            // 显示加载中的状态
            Text(text = "Loading...")
        }

        is Status.Error -> {
            // 显示错误信息
            Text(text = "Error: ${(dataState as Status.Error).message}")
        }

        is Status.Success -> {
            val blocks = (dataState as Status.Success<List<Block>>).data ?: emptyList()
            BlockItem(blocks)
        }
    }
}