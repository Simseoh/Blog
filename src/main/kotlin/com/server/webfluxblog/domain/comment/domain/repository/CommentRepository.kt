package com.server.webfluxblog.domain.comment.domain.repository

import com.server.webfluxblog.domain.comment.domain.entity.CommentEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CommentRepository : CoroutineCrudRepository<CommentEntity, Long> {
    fun findByPostId(postId: Long): Flow<CommentEntity>
}