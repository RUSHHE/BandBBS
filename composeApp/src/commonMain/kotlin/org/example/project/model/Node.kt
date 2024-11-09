package com.example.bandbbs.http.model

data class Node(
    val description: String,
    val post: String,
    val reply: String,
    val extra: Extra,
)
