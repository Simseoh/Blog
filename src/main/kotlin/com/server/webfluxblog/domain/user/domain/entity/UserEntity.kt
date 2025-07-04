package com.server.webfluxblog.domain.user.domain.entity

import com.server.webfluxblog.domain.user.domain.enums.UserRole
import jakarta.validation.constraints.Email
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table(name = "users")
data class UserEntity(
    @Id
    val id: Long? = null,
    val email: String,
    val role: UserRole = UserRole.ROLE_USER,
    val username: String,
    val password: String,
    val lastLoginAt: LocalDateTime? = null,

    @CreatedDate
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @LastModifiedDate
    val updatedAt: LocalDateTime = LocalDateTime.now()
)