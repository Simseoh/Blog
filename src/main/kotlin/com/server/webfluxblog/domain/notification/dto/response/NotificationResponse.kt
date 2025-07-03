package com.server.webfluxblog.domain.notification.dto.response

import java.time.LocalDateTime

data class NotificationResponse (
    val id: Long,
    val userId: Long,
    val message: String,
    val isRead: Boolean,
    val createdAt: LocalDateTime
)