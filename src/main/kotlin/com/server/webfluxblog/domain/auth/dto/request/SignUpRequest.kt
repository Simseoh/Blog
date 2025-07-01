package com.server.webfluxblog.domain.auth.dto.request

import jakarta.validation.constraints.Email

data class SignUpRequest(
    @field:Email(message = "not email form")
    val email: String,
    val username: String,
    val password: String
)
