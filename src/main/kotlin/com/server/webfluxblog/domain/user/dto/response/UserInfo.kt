package com.server.webfluxblog.domain.user.dto.response

import com.server.webfluxblog.domain.user.domain.entity.UserEntity
import com.server.webfluxblog.domain.user.domain.enums.UserRole
import java.time.LocalDateTime

data class UserInfo(
    val id : Long,
    val email : String,
    val role : UserRole,
    val username: String,
    val createdAt: LocalDateTime = LocalDateTime.now()
) {
    fun mapUserToDto(user: UserEntity) : UserInfo {
        return UserInfo(user.id!!, user.email, user.role, user.username, user.createdAt)
    }
}