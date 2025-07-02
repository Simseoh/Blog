package com.server.webfluxblog.domain.notification.domain.repository

import com.server.webfluxblog.domain.notification.domain.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface NotificationRepository : CoroutineCrudRepository<NotificationEntity, Long> {
    fun findByUserIdAndIsReadFalse(userId: Long): Flow<NotificationEntity>
}