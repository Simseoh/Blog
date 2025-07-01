package com.server.webfluxblog.domain.announcement.service.impl

import com.server.webfluxblog.domain.announcement.domain.entity.AnnouncementEntity
import com.server.webfluxblog.domain.announcement.dto.request.AnnouncementRequest
import com.server.webfluxblog.domain.announcement.repository.AnnouncementRepository
import com.server.webfluxblog.domain.announcement.service.AnnouncementService
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AnnouncementServiceImpl(
    private val announcementRepository: AnnouncementRepository
) : AnnouncementService {
    override suspend fun createAnnouncement(request: AnnouncementRequest): AnnouncementEntity {
        val announcement = AnnouncementEntity(
            title = request.title,
            content = request.content,
        )
        return announcementRepository.save(announcement)
    }
    override fun getAnnouncements(): Flow<AnnouncementEntity>? = announcementRepository.findAllByOrderByCreatedAtDesc()
}