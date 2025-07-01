package com.server.webfluxblog.domain.comment.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "comment")
data class CommentEntity(
    @Id
    val id: Long? = null,
    val postId: Long,
    val userId: Long,
    val content: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
)