package com.server.webfluxblog.domain.auth.domain.repository

import com.server.webfluxblog.global.security.jwt.property.JwtProperties
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
class RefreshTokenRepositoryImpl(
    private val jwtProperties: JwtProperties,
    private val redisTemplate: ReactiveRedisTemplate<String, String>
) : RefreshTokenRepository {
    override fun save(email: String, refreshToken: String): Flow<Unit> = flow {
        val key = "refreshToken:$email"
        redisTemplate.opsForValue()
            .set(key, refreshToken, Duration.ofMillis(jwtProperties.refreshTokenExpiration))
            .awaitSingle()
        emit(Unit)
    }

    override fun findByEmail(email: String): Flow<String> = flow {
        val key = "refreshToken:$email"
        val token = redisTemplate.opsForValue()
            .get(key)
            .awaitFirstOrNull()
        if (token != null) emit(token)
    }

    override fun deleteByEmail(email: String): Flow<Boolean> = flow {
        val key = "refreshToken:$email"
        val deletedCount = redisTemplate.delete(key).awaitSingle()
        emit(deletedCount > 0)
    }

    override fun existsByEmail(email: String): Flow<Boolean> = flow {
        val key = "refreshToken:$email"
        val exists = redisTemplate.hasKey(key).awaitSingle()
        emit(exists)
    }
}