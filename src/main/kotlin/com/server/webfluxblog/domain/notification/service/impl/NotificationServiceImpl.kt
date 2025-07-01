package com.server.webfluxblog.domain.notification.service.impl

import com.server.webfluxblog.domain.notification.domain.entity.NotificationEntity
import com.server.webfluxblog.domain.notification.repository.NotificationRepository
import com.server.webfluxblog.domain.notification.service.NotificationService
import com.server.webfluxblog.global.security.holder.SecurityHolder
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class NotificationServiceImpl(
    private val notificationRepository: NotificationRepository,
    private val securityHolder: SecurityHolder
) : NotificationService {

    override suspend fun getNotifications(): Flow<NotificationEntity> {
        val userId : Long = securityHolder.getPrincipal()?.id!!
        return notificationRepository.findByUserIdAndIsReadFalse(userId)
    }

    override suspend fun notifyUser(userId: Long, message: String) {
        notificationRepository.save(NotificationEntity(userId = userId, message = message))
    }
}