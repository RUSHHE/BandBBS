package org.example.project.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.UIKitView
import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKWebView

@Composable
actual fun LoginPage() {
    UIKitView(factory = {
        WKWebView().apply {
            loadRequest(NSURLRequest(NSURL(string = "https://bandbbs.cn")))
        }
    }, modifier = Modifier.fillMaxSize())
}