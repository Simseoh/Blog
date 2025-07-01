package com.server.webfluxblog.domain.notification.controller

import com.server.webfluxblog.domain.notification.domain.entity.NotificationEntity
import com.server.webfluxblog.domain.notification.service.NotificationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.flow.toList
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("notification")
@Tag(name = "Notification")
class NotificationController(
    private val notificationService: NotificationService
) {
    @Operation(summary = "알림 수신")
    @GetMapping("/notifications")
    suspend fun getNotifications(principal: Principal): List<NotificationEntity>? =
        notificationService.getNotifications().toList()
}