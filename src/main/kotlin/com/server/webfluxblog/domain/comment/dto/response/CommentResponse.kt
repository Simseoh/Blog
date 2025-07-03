package com.server.webfluxblog.domain.comment.dto.response

import java.time.LocalDateTime

data class CommentResponse(
    val id: Long,
    val postId: Long,
    val userId: Long,
    val content: String,
    val createdAt: LocalDateTime
)
