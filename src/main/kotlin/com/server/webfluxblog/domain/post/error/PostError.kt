package com.server.webfluxblog.domain.auth.error

import com.server.webfluxblog.global.exception.CustomError
import org.springframework.http.HttpStatus

enum class PostError(
    override val status: HttpStatus,
    override val message: String,
) : CustomError {
    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "post not found")
}
