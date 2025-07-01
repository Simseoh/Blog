package com.server.webfluxblog.domain.blog.dto.request

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

data class PostPostRequest(
    val title: String = "",
    val content: String = "",
)
