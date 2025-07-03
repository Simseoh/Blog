package com.server.webfluxblog.domain.announcement.dto.response

import java.time.LocalDateTime

data class AnnouncementResponse(
    val id: Long,
    val title: String,
    val content: String,
    val createdAt: LocalDateTime
)
