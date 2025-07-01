package com.server.webfluxblog.domain.auth.service

import com.server.webfluxblog.domain.auth.dto.request.LoginRequest
import com.server.webfluxblog.domain.auth.dto.request.SignUpRequest
import com.server.webfluxblog.domain.auth.error.AuthError
import com.server.webfluxblog.domain.auth.repository.RefreshTokenRepository
import com.server.webfluxblog.domain.user.domain.entity.UserEntity
import com.server.webfluxblog.domain.user.domain.enums.UserRole
import com.server.webfluxblog.domain.user.repository.UserRepository
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
    private val encoder: PasswordEncoder
) {
    suspend fun signup(request: SignUpRequest) {
        if (userRepository.existsByEmail(request.email)) {
            throw CustomException(AuthError.EMAIL_ALREADY_IN_USE)
        } else {
            userRepository.save(
                UserEntity(
                    email = request.email,
                    role = UserRole.ROLE_USER,
                    username =  request.username ,
                    password =  encoder.encode(request.password)
                )
            )
        }
    }

    suspend fun login(request: LoginRequest) : Jwt {
        val user:UserEntity = userRepository.findByEmail(request.email)
            ?: throw CustomException(AuthError.USER_NOT_FOUND)
        if ( !encoder.matches(request.password, user.password) ) {
            throw CustomException(AuthError.PASSWORD_WRONG)
        }
        val tokens : Jwt = jwtProvider.generateToken(user)

        userRepository.save(user.copy(lastLoginDate = LocalDateTime.now()))
        refreshTokenRepository.save(user.email, tokens.refreshToken)
        return tokens
    }
}
