package org.example.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import org.example.project.di.initKoin

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "BandBBS",
    ) {
        // Desktop端初始化koin
        initKoin()

        App()
    }
}