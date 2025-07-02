package com.server.webfluxblog.domain.announcement.domain.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "announcements")
data class AnnouncementEntity(
    @Id
    val id: Long? = null,
    val title: String,
    val content: String,
    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now()
)