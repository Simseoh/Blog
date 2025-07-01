package com.server.webfluxblog.global.security.holder.impl

import com.server.webfluxblog.domain.user.domain.entity.UserEntity
import com.server.webfluxblog.domain.user.repository.UserRepository
import com.server.webfluxblog.global.security.holder.SecurityHolder
import lombok.RequiredArgsConstructor
import org.springdoc.core.service.SecurityService
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class SecurityHolderImpl(
    private val userRepository: UserRepository
) : SecurityHolder {
    override suspend fun getPrincipal(): UserEntity? {
        val email: String = SecurityContextHolder.getContext().authentication.principal as String

        return userRepository.findByEmail(email)
    }
}