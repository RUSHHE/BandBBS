package org.example.project

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
    val contentDescription: String,
    val route: String,
) {
    HOME("首页", Icons.Default.Home, "首页", "home"),
    RESOURCE("资源", Icons.Default.Category, "资源", "resource"),
    DISCOVER("发现", Icons.Default.Dashboard, "发现", "discover"),
    PROFILE("我的", Icons.Default.Person, "我的", "profile"),
}