package com.server.webfluxblog.domain.auth.domain.repository

import com.server.webfluxblog.global.security.jwt.property.JwtProperties
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.time.Duration

@Repository
class RefreshTokenRepositoryImpl(
    private val jwtProperties: JwtProperties,
    private val redisTemplate: ReactiveRedisTemplate<String, String>
) : RefreshTokenRepository {
    override fun save(email: String, refreshToken: String): Mono<Void> {
        val key = "refreshToken:$email"

        return redisTemplate.opsForValue()
            .set(key, refreshToken, Duration.ofMillis(jwtProperties.refreshTokenExpiration))
            .then()
    }

    override fun findByEmail(email: String): Mono<String> {
        val key = "refreshToken:$email"
        return redisTemplate.opsForValue().get(key)
    }

    override fun deleteByEmail(email: String): Mono<Boolean> {
        val key = "refreshToken:$email"
        return redisTemplate.delete(key)
            .map { it > 0 }
    }

    override fun existsByEmail(email: String): Mono<Boolean> {
        val key = "refreshToken:$email"
        return redisTemplate.hasKey(key)
    }
}