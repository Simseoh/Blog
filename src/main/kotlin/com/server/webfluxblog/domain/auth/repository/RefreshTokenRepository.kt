package com.server.webfluxblog.domain.auth.repository

import java.util.Optional

interface RefreshTokenRepository {

    fun save(email: String, refreshToken: String)

    fun findByEmail(email: String) : Optional<String>

    fun deleteByEmail(email: String)

    fun existsByEmail(email: String): Boolean

}