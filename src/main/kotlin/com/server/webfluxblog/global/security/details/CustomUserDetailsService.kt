package com.server.webfluxblog.global.security.details

import com.server.webfluxblog.domain.user.domain.repository.UserRepository
import kotlinx.coroutines.reactor.mono
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class CustomReactiveUserDetailsService(
    private val userRepository: UserRepository
) : ReactiveUserDetailsService {
    override fun findByUsername(email: String): Mono<UserDetails> = mono {
        val user = userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("User not found: $email")
        CustomUserDetails(user)
    }
}