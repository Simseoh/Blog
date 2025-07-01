package com.server.webfluxblog.domain.auth.repository

import com.server.webfluxblog.global.security.jwt.property.JwtProperties
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.*
import java.util.concurrent.TimeUnit

@Repository
class RefreshTokenRepositoryImpl(
    private val jwtProperties: JwtProperties,
    private val redisTemplate: RedisTemplate<String, String>
) : RefreshTokenRepository {
    override fun save(email: String, refreshToken: String) {
        redisTemplate.opsForValue().set("refreshToken:" + email, refreshToken, jwtProperties.refreshTokenExpiration, TimeUnit.MILLISECONDS);
    }

    override fun findByEmail(email: String) : Optional<String> {
        return Optional.ofNullable(redisTemplate.opsForValue().get("refreshToken:" + email));
    }

    override fun deleteByEmail(email: String) {
        redisTemplate.delete("refreshToken:" + email);
    }

    override fun existsByEmail(email: String): Boolean {
        return Boolean.equals(redisTemplate.opsForValue().get("refreshToken:" + email))
    }
}