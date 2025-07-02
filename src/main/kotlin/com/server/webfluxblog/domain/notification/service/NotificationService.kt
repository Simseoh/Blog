package com.server.webfluxblog.domain.notification.service

import com.server.webfluxblog.domain.notification.dto.response.NotificationResponse
import kotlinx.coroutines.flow.Flow

interface NotificationService {
    suspend fun getNotifications(): Flow<NotificationResponse>
    suspend fun notifyUser(postId: Long, message: String)
}