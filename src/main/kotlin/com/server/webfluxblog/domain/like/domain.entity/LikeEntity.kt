package com.server.webfluxblog.domain.like.domain.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "Likes")
data class LikeEntity(
    @Id
    val id: Long? = null,
    val postId: Long,
    val userId: Long,
    val createdAt: LocalDateTime = LocalDateTime.now()
)