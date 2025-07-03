package com.server.webfluxblog.domain.like.domain.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "likes")
data class LikeEntity(
    @Id
    val id: Long? = null,
    val postId: Long,
    val userId: Long,

    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now()
)