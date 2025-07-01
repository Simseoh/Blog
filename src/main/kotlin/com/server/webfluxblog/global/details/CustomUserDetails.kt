package com.server.webfluxblog.global.details

import com.server.webfluxblog.domain.user.domain.entity.UserEntity
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component

class CustomUserDetails(
    val user: UserEntity
) : UserDetails {

    override fun getUsername(): String {
        return user.email
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(SimpleGrantedAuthority(user.role.name))
    }

}