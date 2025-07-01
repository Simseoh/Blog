package com.server.webfluxblog.domain.notification.service

import com.server.webfluxblog.domain.notification.domain.entity.NotificationEntity
import kotlinx.coroutines.flow.Flow

interface NotificationService {
    suspend fun getNotifications(): Flow<NotificationEntity>
    suspend fun notifyUser(postId: Long, message: String)
}