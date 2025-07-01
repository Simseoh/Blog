package com.server.webfluxblog.domain.comment.dto.request

data class CommentRequest(
    val postId: Long,
    val content: String
)
