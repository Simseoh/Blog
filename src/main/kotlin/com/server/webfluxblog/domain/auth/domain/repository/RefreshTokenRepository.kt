package com.server.webfluxblog.domain.auth.domain.repository

import kotlinx.coroutines.flow.Flow

interface RefreshTokenRepository {
    fun save(email: String, refreshToken: String): Flow<Unit>
    fun findByEmail(email: String): Flow<String>
    fun deleteByEmail(email: String): Flow<Boolean>
    fun existsByEmail(email: String): Flow<Boolean>
}