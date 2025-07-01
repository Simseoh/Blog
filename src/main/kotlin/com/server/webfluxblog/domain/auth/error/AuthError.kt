package com.server.webfluxblog.domain.auth.error

import com.server.webfluxblog.global.exception.CustomError
import org.springframework.http.HttpStatus

enum class AuthError(
    override val status: HttpStatus,
    override val message: String,
) : CustomError {
    PASSWORD_WRONG(HttpStatus.BAD_REQUEST, "Password does not match request"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    EMAIL_ALREADY_IN_USE(HttpStatus.FORBIDDEN, "Email already in use"),
}
