package com.server.webfluxblog.domain.auth.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @field:Email(message = "not email form")
    @field:NotBlank(message = "email required")
    var email: String,
    @field:NotBlank(message = "password required")
    var password: String
)
