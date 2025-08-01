package com.server.webfluxblog.domain.auth.error

import com.server.webfluxblog.global.exception.CustomError
import org.springframework.http.HttpStatus

enum class AnnouncementError(
    override val status: HttpStatus,
    override val message: String,
) : CustomError {
    ANNOUNCEMENT_NOT_FOUND(HttpStatus.NOT_FOUND, "Announcement not found."),
}
