package com.server.webfluxblog.domain.auth.domain.repository

import reactor.core.publisher.Mono

interface RefreshTokenRepository {
    fun save(email: String, refreshToken: String): Mono<Void>
    fun findByEmail(email: String): Mono<String>
    fun deleteByEmail(email: String): Mono<Boolean>
    fun existsByEmail(email: String): Mono<Boolean>
}