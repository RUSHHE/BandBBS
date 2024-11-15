package org.example.project.model

data class NewThreads(
    val title: String,
    val authorName: String,
    val authorAvatar: String,
    val label: String? = null,
    val watch: String? = null,
    val reply: String? = null,
    val time: String,
) : Discover()