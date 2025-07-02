package com.server.webfluxblog.domain.post.domain.repository

import com.server.webfluxblog.domain.post.domain.entity.PostEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PostRepository : CoroutineCrudRepository<PostEntity, Long> {
    fun findAllByOrderByCreatedAtDesc(): Flow<PostEntity>
    fun findByTitleContainingIgnoreCase(title: String): Flow<PostEntity>
}
