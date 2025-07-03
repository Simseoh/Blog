package com.server.webfluxblog.domain.announcement.controller

import com.server.webfluxblog.domain.announcement.dto.request.AnnouncementRequest
import com.server.webfluxblog.domain.announcement.service.AnnouncementService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/announcements")
@Tag(name = "Announcements")
class AnnouncementController(
    private val announcementService: AnnouncementService
) {
    @Operation(summary = "공지사항 게시")
    @PostMapping
    suspend fun createAnnouncement(@RequestBody request: AnnouncementRequest) = announcementService.createAnnouncement(request)

    @Operation(summary = "공지사항 조회")
    @GetMapping
    fun getAnnouncements() = announcementService.getAnnouncements()
}