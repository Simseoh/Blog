package com.server.webfluxblog.domain.post.domain.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "posts")
data class PostEntity(
    @Id
    val id: Long? = null,
    val title: String,
    val content: String,
    val userId: Long,
    var likeCount: Long,
    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @LastModifiedDate
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    fun addLike() : PostEntity {
        likeCount += 1L
        return this
    }

    fun minusLike() : PostEntity {
        likeCount -= 1L
        return this
    }
}