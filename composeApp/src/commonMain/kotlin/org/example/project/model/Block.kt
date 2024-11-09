package com.example.bandbbs.http.model

data class Block(
    val id: Int? = null,
    val name: String,
    val node: List<Node>,
)