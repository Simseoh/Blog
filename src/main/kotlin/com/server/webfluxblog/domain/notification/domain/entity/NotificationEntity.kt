package com.server.webfluxblog.domain.notification.domain.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "notifications")
data class NotificationEntity(
    @Id
    val id: Long? = null,
    val userId: Long,
    val message: String,
    val isRead: Boolean = false,
    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now()
)