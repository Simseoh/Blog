package com.server.webfluxblog.domain.notification.controller

import com.server.webfluxblog.domain.notification.dto.response.NotificationResponse
import com.server.webfluxblog.domain.notification.service.NotificationService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.flow.toList
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.security.Principal

@RestController
@RequestMapping("/notifications")
@Tag(name = "Notification")
class NotificationController(
    private val notificationService: NotificationService
) {
    @Operation(summary = "알림 수신")
    @GetMapping
    suspend fun getNotifications(principal: Principal) =
        notificationService.getNotifications().toList()
}