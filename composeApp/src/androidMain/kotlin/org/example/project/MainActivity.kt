package org.example.project

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.bandbbs.http.model.Block
import com.example.bandbbs.http.model.Extra
import com.example.bandbbs.http.model.Node
import org.example.project.component.ResourceItem
import org.example.project.model.Resource

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}

@Preview(showBackground = true)
@Composable
fun BlockItemPreview() {
    val block = Block(
        name = "Test Block",
        node = listOf(
            Node(
                description = "Test Node",
                post = "1k",
                reply = "123",
                extra = Extra(
                    title = "title",
                    avatar = "https://profile-avatar.csdnimg.cn/default.jpg!1",
                    username = "John Doe",
                    time = "2023-05-01"
                )
            ),
            Node(
                description = "Test Node",
                post = "This is a test node.",
                reply = "123",
                extra = Extra(
                    title = "title",
                    avatar = "",
                    username = "John Doe",
                    time = "2023-05-01"
                )
            )
        )
    )
    BlockItem(listOf(block))
}

@Preview(showBackground = true)
@Composable
fun ResourceItemPreview() {
    ResourceItem(
        Resource(
            title = "Test Resource",
            subTitle = "https://example.com/test-resource",
            version = "1.0.0",
            icon = "",
            category = "Test Category",
            label = "Test Label",
            score = "0.0",
            download = "0",
            authorName = "John Doe",
            authorAvatar = "",
            time = "2023-05-01",
            )
        )
}