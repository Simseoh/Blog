package com.server.webfluxblog.domain.announcement.service

import com.server.webfluxblog.domain.announcement.domain.entity.AnnouncementEntity
import com.server.webfluxblog.domain.announcement.dto.request.AnnouncementRequest
import com.server.webfluxblog.domain.announcement.dto.response.AnnouncementResponse
import com.server.webfluxblog.domain.announcement.domain.repository.AnnouncementRepository
import com.server.webfluxblog.domain.auth.error.AnnouncementError
import com.server.webfluxblog.global.exception.CustomException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class AnnouncementService(
    private val announcementRepository: AnnouncementRepository
) {
    suspend fun createAnnouncement(request: AnnouncementRequest): AnnouncementResponse {
        val announcement = AnnouncementEntity(
            title = request.title,
            content = request.content,
        )
        return announcementRepository.save(announcement).toResponse()
    }
    fun getAnnouncements(): Flow<AnnouncementResponse> {
        val announcements = announcementRepository.findAllByOrderByCreatedAtDesc() ?: throw CustomException(AnnouncementError.ANNOUNCEMENT_NOT_FOUND)
        return announcements.map { it.toResponse() }
    }

    private fun AnnouncementEntity.toResponse() : AnnouncementResponse = AnnouncementResponse(
        id = id!!,
        title = title,
        content = content,
        createdAt = createdAt,
    )
}