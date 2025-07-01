package com.server.webfluxblog.domain.auth.controller

import com.server.webfluxblog.domain.auth.dto.request.LoginRequest
import com.server.webfluxblog.domain.auth.dto.request.SignUpRequest
import com.server.webfluxblog.domain.auth.service.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth")
class AuthController(
    val authService: AuthService
) {
    @Operation(summary = "회원가입")
    @PostMapping("/signup")
    suspend fun signUp(request: SignUpRequest) = authService.signup(request)

    @Operation(summary = "로그인")
    @PostMapping("/login")
    suspend fun login(request: LoginRequest) = authService.login(request)


}