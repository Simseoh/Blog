package com.server.webfluxblog.domain.post.dto.request

data class CreatePostRequest(
    val title: String,
    val content: String,
)
