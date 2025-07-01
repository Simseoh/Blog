package com.server.webfluxblog.domain.auth.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class LoginRequest(
    @Email(message = "not email form")
    @NotBlank(message = "email required")
    val email: String,
    @NotBlank(message = "password required")
    val password: String
)
