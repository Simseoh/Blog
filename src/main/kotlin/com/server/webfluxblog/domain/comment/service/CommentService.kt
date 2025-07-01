package com.server.webfluxblog.domain.comment.service

import com.server.webfluxblog.domain.comment.domain.entity.CommentEntity
import com.server.webfluxblog.domain.comment.dto.request.CommentRequest
import kotlinx.coroutines.flow.Flow

interface CommentService {
    suspend fun addComment(request: CommentRequest): CommentEntity
    fun getComments(postId: Long): Flow<CommentEntity>
}