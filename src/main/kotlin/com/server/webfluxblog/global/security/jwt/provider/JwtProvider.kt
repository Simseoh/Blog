package com.server.webfluxblog.global.security.jwt.provider

import com.server.webfluxblog.domain.auth.error.PostError
import com.server.webfluxblog.domain.auth.domain.repository.RefreshTokenRepository
import com.server.webfluxblog.domain.user.domain.entity.UserEntity
import com.server.webfluxblog.domain.user.domain.repository.UserRepository
import com.server.webfluxblog.global.exception.CustomException
import com.server.webfluxblog.global.security.jwt.property.JwtProperties
import com.server.webfluxblog.global.security.jwt.enums.JwtType
import io.jsonwebtoken.Jwts
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import java.nio.charset.StandardCharsets
import java.util.*
import javax.crypto.SecretKey
import javax.crypto.spec.SecretKeySpec
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class JwtProvider(
    private val jwtProperties: JwtProperties,
    private val refreshTokenRepository: RefreshTokenRepository,
    private val userRepository: UserRepository,
    private val userDetailsService: ReactiveUserDetailsService,
) {
    private val key: SecretKey by lazy {
        SecretKeySpec(
            jwtProperties.secretKey.toByteArray(StandardCharsets.UTF_8),
            Jwts.SIG.HS512.key().build().algorithm
        )
    }

    fun getUserId(token: String): String {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .body
            .subject
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getType(token: String): JwtType {
        val jws = Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)

        val type = jws.header["typ"] as String
        return JwtType.valueOf(type)
    }

    fun generateToken(user: UserEntity): Jwt {
        val accessToken = generateAccessToken(user)
        val refreshToken = generateRefreshToken(user)

        return Jwt(accessToken, refreshToken)
    }

    fun getAuthentication(token: String): Mono<Authentication> {
        val userId = getUserId(token)
        return userDetailsService.findByUsername(userId)
            .switchIfEmpty(Mono.error(CustomException(PostError.USER_NOT_FOUND)))
            .map { userDetails ->
                UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
            }
    }

    fun extractToken(exchange: ServerWebExchange): String? {
        val token = exchange.request.headers.getFirst("Authorization")
        return if (token != null && token.startsWith("Bearer ")) {
            token.substring(7)
        } else null
    }

    private fun generateAccessToken(user: UserEntity): String {
        val now = Date()
        return Jwts.builder()
            .header()
            .type(JwtType.ACCESS.name)
            .and()
            .subject(user.email)
            .signWith(key)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + jwtProperties.accessTokenExpiration))
            .compact()
    }

    private fun generateRefreshToken(user: UserEntity): String {
        val now = Date()
        return Jwts.builder()
            .header()
            .type(JwtType.REFRESH.name)
            .and()
            .subject(user.email)
            .signWith(key)
            .setIssuedAt(now)
            .setExpiration(Date(now.time + jwtProperties.accessTokenExpiration))
            .compact()
    }
}

data class Jwt(
    val accessToken : String,
    val refreshToken : String
)