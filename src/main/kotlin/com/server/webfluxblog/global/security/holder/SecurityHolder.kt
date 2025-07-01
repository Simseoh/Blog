package com.server.webfluxblog.global.security.holder

import com.server.webfluxblog.domain.user.domain.entity.UserEntity

interface SecurityHolder {
    suspend fun getPrincipal(): UserEntity?
}