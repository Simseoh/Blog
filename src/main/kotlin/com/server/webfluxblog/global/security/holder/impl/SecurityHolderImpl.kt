package com.server.webfluxblog.global.security.holder.impl

import com.server.webfluxblog.domain.user.domain.entity.UserEntity
import com.server.webfluxblog.domain.user.repository.UserRepository
import com.server.webfluxblog.global.security.holder.SecurityHolder
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.stereotype.Component

@Component
class SecurityHolderImpl(
    private val userRepository: UserRepository
) : SecurityHolder {
    override suspend fun getPrincipal(): UserEntity? {
        val securityContext = ReactiveSecurityContextHolder.getContext().awaitSingleOrNull() ?: return null
        val authentication = securityContext.authentication ?: return null
        val principal = authentication.principal
        val email = when (principal) {
            is String -> principal
            is org.springframework.security.core.userdetails.UserDetails -> principal.username
            else -> null
        } ?: return null

        return userRepository.findByEmail(email)
    }
}