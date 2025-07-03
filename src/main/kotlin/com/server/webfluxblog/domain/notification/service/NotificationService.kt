package com.server.webfluxblog.domain.notification.service

import com.server.webfluxblog.domain.notification.domain.entity.NotificationEntity
import com.server.webfluxblog.domain.notification.dto.response.NotificationResponse
import com.server.webfluxblog.domain.notification.domain.repository.NotificationRepository
import com.server.webfluxblog.global.security.holder.SecurityHolder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class NotificationService(
    private val notificationRepository: NotificationRepository,
    private val securityHolder: SecurityHolder
) {

    suspend fun getNotifications(): Flow<NotificationResponse> {
        val userId : Long = securityHolder.getPrincipal()?.id!!
        return notificationRepository.findByUserIdAndIsReadFalse(userId).map { it.toResponse() }
    }

    suspend fun notifyUser(userId: Long, message: String) {
        notificationRepository.save(NotificationEntity(userId = userId, message = message))
    }

    private fun NotificationEntity.toResponse(): NotificationResponse {
        return NotificationResponse(
            id = id!!,
            userId = userId,
            message = message,
            isRead = isRead,
            createdAt = createdAt
        )
    }
}