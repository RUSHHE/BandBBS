package org.example.project.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Article
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter
import com.example.bandbbs.http.model.Block
import com.example.bandbbs.http.model.Node
import org.example.project.Status
import org.example.project.theme.ThemeManager
import org.example.project.theme.ThemeType
import org.example.project.viewmodel.HomeViewModel
import org.koin.compose.viewmodel.koinViewModel
import top.yukonga.miuix.kmp.basic.Card as MiuixCard
import top.yukonga.miuix.kmp.basic.Icon as MiuixIcon
import top.yukonga.miuix.kmp.basic.Text as MiuixText
import top.yukonga.miuix.kmp.utils.HorizontalDivider as MiuixHorizontalDivider

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

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun BlockItem(blocks: List<Block>) {
    LazyColumn {
        blocks.forEach { block ->
            // 使用 stickyHeader 来展示 Block 的 name 作为头部
            stickyHeader {
                if (ThemeManager.currentTheme == ThemeType.Miuix) {
                    MiuixText(
                        text = block.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                    )
                } else {
                    Text(
                        text = block.name,
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                    )
                }
            }

            // 展示 block 内部的 node 列表
            items(block.node) { node ->
                NodeItem(node)

                if (ThemeManager.currentTheme == ThemeType.Miuix) {
                    MiuixHorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                    )
                } else {
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outline,
                    )
                }
            }
        }
    }
}

@Composable
fun NodeItem(node: Node) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        if (ThemeManager.currentTheme == ThemeType.Miuix) {
            MiuixText(
                text = node.description,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 4.dp) // 减少与下方元素的间距
            )

            Row {
                TagItem(Icons.AutoMirrored.Outlined.Article, node.post)
                Spacer(modifier = Modifier.width(8.dp))
                TagItem(Icons.Default.ChatBubbleOutline, node.reply)
            }

            Spacer(modifier = Modifier.height(8.dp))

            ExtraItem(node)
        } else {
            Text(
                text = node.description,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 4.dp) // 减少与下方元素的间距
            )

            Row {
                TagItem(Icons.AutoMirrored.Outlined.Article, node.post)
                Spacer(modifier = Modifier.width(8.dp))
                TagItem(Icons.Default.ChatBubbleOutline, node.reply)
            }

            Spacer(modifier = Modifier.height(8.dp))

            ExtraItem(node)
        }
    }
}

@Composable
private fun TagItem(icon: ImageVector, text: String) {
    if (ThemeManager.currentTheme == ThemeType.Miuix) {
        MiuixCard(
            modifier = Modifier.padding(4.dp) // 减少卡片与周围的间距
        ) {
            Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
                MiuixIcon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 4.dp)
                )
                MiuixText(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    } else {
        Card(
            shape = MaterialTheme.shapes.extraLarge,
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.primaryContainer),
            modifier = Modifier.padding(4.dp) // 减少卡片与周围的间距
        ) {
            Row(modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    modifier = Modifier.padding(end = 4.dp)
                )
                Text(
                    text = text,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun ExtraItem(node: Node) {
    if (ThemeManager.currentTheme == ThemeType.Miuix) {
        MiuixCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp) // 调整卡片边距
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Row {
                    Image(
                        painter = rememberAsyncImagePainter(node.extra.avatar),
                        contentDescription = "头像",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        MiuixText(
                            text = node.extra.username,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        MiuixText(
                            text = node.extra.time,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                MiuixText(
                    text = node.extra.title,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    } else {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp) // 调整卡片边距
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Row {
                    Image(
                        painter = rememberAsyncImagePainter(node.extra.avatar),
                        contentDescription = "头像",
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Text(text = node.extra.username, style = MaterialTheme.typography.bodyLarge)
                        Text(text = node.extra.time, style = MaterialTheme.typography.bodySmall)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = node.extra.title, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}