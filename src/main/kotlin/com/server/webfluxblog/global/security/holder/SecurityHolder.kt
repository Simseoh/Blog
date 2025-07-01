package com.server.webfluxblog.global.security.holder

import com.server.webfluxblog.domain.user.domain.entity.UserEntity
import org.springframework.security.core.userdetails.UserDetails

interface SecurityHolder {
    suspend fun getPrincipal(): UserEntity?
}