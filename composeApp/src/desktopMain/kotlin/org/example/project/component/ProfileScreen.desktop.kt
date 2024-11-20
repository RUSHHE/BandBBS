package org.example.project.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import me.friwi.jcefmaven.CefAppBuilder
import org.cef.CefApp
import java.awt.BorderLayout
import javax.swing.JPanel

object CefAppManager {
    val app: CefApp by lazy {
        CefAppBuilder().apply {
            cefSettings.windowless_rendering_enabled = false
            addJcefArgs("")
        }.build()
    }
}

@Composable
actual fun LoginPage() {
    val url by remember { mutableStateOf("https://bandbbs.cn") }

    val build = CefAppManager.app

    val client = remember { build.createClient() }
    val browser = remember { client.createBrowser(url, false, false) }
    val uiComponent = remember { browser.uiComponent }

    val panel = remember {
        JPanel().apply {
            layout = BorderLayout()
            add(uiComponent, BorderLayout.CENTER)
        }
    }

    SwingPanel(
        factory = { panel },
        modifier = Modifier.fillMaxSize()
    )

    DisposableEffect(Unit) {
        onDispose {
            browser.close(true)
        }
    }
}