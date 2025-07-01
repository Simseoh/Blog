package com.server.webfluxblog.global.security.jwt.property

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
data class JwtProperties(
    @Value("\${jwt.secret-key}") val secretKey: String,
    @Value("\${jwt.access-token-expiration}") val accessTokenExpiration: Long,
    @Value("\${jwt.refresh-token-expiration}") val refreshTokenExpiration: Long
)