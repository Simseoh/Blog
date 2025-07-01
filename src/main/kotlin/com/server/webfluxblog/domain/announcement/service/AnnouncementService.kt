package com.server.webfluxblog.domain.announcement.service

import com.server.webfluxblog.domain.announcement.domain.entity.AnnouncementEntity
import com.server.webfluxblog.domain.announcement.dto.request.AnnouncementRequest
import kotlinx.coroutines.flow.Flow

interface AnnouncementService {
    suspend fun createAnnouncement(request: AnnouncementRequest): AnnouncementEntity
    fun getAnnouncements(): Flow<AnnouncementEntity>?
}