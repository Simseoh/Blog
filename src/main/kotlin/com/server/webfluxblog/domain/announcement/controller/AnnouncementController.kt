package com.server.webfluxblog.domain.announcement.controller

import com.server.webfluxblog.domain.announcement.domain.entity.AnnouncementEntity
import com.server.webfluxblog.domain.announcement.dto.request.AnnouncementRequest
import com.server.webfluxblog.domain.announcement.service.AnnouncementService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.flow.Flow
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/announcements")
@Tag(name = "Announcement")
class AnnouncementController(
    private val announcementService: AnnouncementService
) {
    @Operation(summary = "공지사항 게시")
    @PostMapping("/announcements")
    suspend fun createAnnouncement(@RequestBody request: AnnouncementRequest): AnnouncementEntity = announcementService.createAnnouncement(request)
    @Operation(summary = "공지사항 조회")
    @GetMapping("/announcements")
    fun getAnnouncements(): Flow<AnnouncementEntity>? = announcementService.getAnnouncements()
}