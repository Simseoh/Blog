package com.server.webfluxblog.domain.auth.service

import com.server.webfluxblog.domain.auth.dto.request.LoginRequest
import com.server.webfluxblog.domain.auth.dto.request.SignUpRequest
import com.server.webfluxblog.domain.auth.error.AuthError
import com.server.webfluxblog.domain.auth.domain.repository.RefreshTokenRepository
import com.server.webfluxblog.domain.notification.service.NotificationService
import com.server.webfluxblog.domain.user.domain.entity.UserEntity
import com.server.webfluxblog.domain.user.domain.enums.UserRole
import com.server.webfluxblog.domain.user.domain.repository.UserRepository
import com.server.webfluxblog.global.exception.CustomException
import com.server.webfluxblog.global.security.jwt.provider.Jwt
import com.server.webfluxblog.global.security.jwt.provider.JwtProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class AuthService(
    private val userRepository: UserRepository,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val jwtProvider: JwtProvider,
    private val passwordEncoder: PasswordEncoder,
) {
    suspend fun signup(request: SignUpRequest) {
        if (userRepository.existsByEmail(request.email))
            throw CustomException(AuthError.EMAIL_ALREADY_IN_USE)

        userRepository.save(
            UserEntity(
                email = request.email,
                username = request.username ,
                password = passwordEncoder.encode(request.password)
            )
        )
    }

    suspend fun login(request: LoginRequest): Jwt {
        val user = userRepository.findByEmail(request.email) ?: throw CustomException(AuthError.USER_NOT_FOUND)

        if (!passwordEncoder.matches(request.password, user.password)) throw CustomException(AuthError.PASSWORD_WRONG)

        val tokens = jwtProvider.generateToken(user)

        userRepository.save(user.copy(lastLoginAt = LocalDateTime.now()))
        refreshTokenRepository.save(user.email, tokens.refreshToken)

        return tokens
    }
}