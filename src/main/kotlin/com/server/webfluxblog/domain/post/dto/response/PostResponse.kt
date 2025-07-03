package com.server.webfluxblog.domain.post.dto.response

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

data class PostResponse(
    val id: Long,
    val title: String,
    val content: String,
    val userId: Long,
    var likeCount: Long,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime
)