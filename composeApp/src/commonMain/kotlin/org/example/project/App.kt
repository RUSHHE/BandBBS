package org.example.project

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil3.compose.rememberAsyncImagePainter
import com.example.bandbbs.http.model.Block
import com.example.bandbbs.http.model.Node
import org.example.project.component.DiscoverScreen
import org.example.project.component.HomeScreen
import org.example.project.component.ProfileScreen
import org.example.project.component.ResourceScreen
import org.example.project.theme.ThemeManager
import org.example.project.theme.ThemeType
import org.jetbrains.compose.ui.tooling.preview.Preview
import top.yukonga.miuix.kmp.basic.NavigationBar
import top.yukonga.miuix.kmp.basic.NavigationItem
import top.yukonga.miuix.kmp.basic.TopAppBar
import top.yukonga.miuix.kmp.theme.MiuixTheme
import top.yukonga.miuix.kmp.basic.Box as MiuixBox
import top.yukonga.miuix.kmp.basic.Card as MiuixCard
import top.yukonga.miuix.kmp.basic.Scaffold as MiuixScaffold
import top.yukonga.miuix.kmp.basic.Text as MiuixText
import top.yukonga.miuix.kmp.utils.HorizontalDivider as MiuixHorizontalDivider

@Composable
@Preview
fun App() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }

    if (ThemeManager.currentTheme == ThemeType.Miuix) {
        MiuixTheme(
            colors = if (isSystemInDarkTheme()) top.yukonga.miuix.kmp.theme.darkColorScheme() else top.yukonga.miuix.kmp.theme.lightColorScheme()
        ) {
            val navController: NavHostController = rememberNavController()
            MiuixScaffold(
                topBar = { MiuixTopAppBar() },
                bottomBar = { MiuixNavigationBar(navController) },
            ) { paddingValues ->
                MiuixBox(Modifier.padding(paddingValues)) {
                    NavHostContainer(navController)
                }
            }
        }
    } else {
        MaterialTheme(
            colorScheme = if (isSystemInDarkTheme()) androidx.compose.material3.darkColorScheme() else androidx.compose.material3.lightColorScheme()
        ) {
            NavigationSuiteScaffold(
                navigationSuiteItems = {
                    AppDestinations.entries.forEach {
                        item(
                            icon = {
                                Icon(
                                    it.icon,
                                    contentDescription = it.contentDescription
                                )
                            },
                            label = { Text(it.label) },
                            selected = it == currentDestination,
                            onClick = { currentDestination = it }
                        )
                    }
                }
            ) {
                when (currentDestination) {
                    AppDestinations.HOME -> HomeScreen()
                    AppDestinations.RESOURCE -> ResourceScreen()
                    AppDestinations.DISCOVER -> DiscoverScreen()
                    AppDestinations.PROFILE -> ProfileScreen()
                }
            }
        }
    }
}

@Composable
fun NavHostContainer(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen()
        }
        composable("resource") {
            ResourceScreen()
        }
        composable("discover") {
            DiscoverScreen()
        }
        composable("profile") {
            ProfileScreen()
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
                MiuixText(text = node.extra.title, style = MaterialTheme.typography.bodyMedium)
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

@Composable
fun MiuixTopAppBar() {
    // TODO: 添加Miuix风格导航栏
    TopAppBar(
        title = "BandBBS",
    )
}

@Composable
fun MiuixNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        items = AppDestinations.entries.map { item ->
            NavigationItem(
                label = item.label,
                icon = item.icon,
            )
        },
        selected = AppDestinations.entries.map { it.route }.indexOf(currentRoute),
        onClick = {
            navController.navigate(AppDestinations.entries[it].route) {
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    )
}