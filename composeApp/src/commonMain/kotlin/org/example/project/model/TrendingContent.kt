package org.example.project.model

data class TrendingContent(
    val title: String,
    val content: String,
    val previewImage: String,
    val authorName: String,
    val authorAvatar: String,
    val replyNumber: String,
    val time: String,
) : Discover()