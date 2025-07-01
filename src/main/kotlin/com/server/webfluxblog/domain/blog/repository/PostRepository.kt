package com.server.webfluxblog.domain.blog.repository

import com.server.webfluxblog.domain.blog.domain.entity.PostEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface PostRepository : CoroutineCrudRepository<PostEntity, Long> {
    fun findAllByOrderByCreatedAtDesc(): Flow<PostEntity>?
    fun findByTitleContainingIgnoreCase(title: String): Flow<PostEntity>
}
