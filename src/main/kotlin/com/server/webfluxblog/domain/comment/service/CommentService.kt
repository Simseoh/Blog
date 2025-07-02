package com.server.webfluxblog.domain.comment.service

import com.server.webfluxblog.domain.comment.domain.entity.CommentEntity
import com.server.webfluxblog.domain.comment.dto.request.CommentRequest
import com.server.webfluxblog.domain.comment.dto.response.CommentResponse
import kotlinx.coroutines.flow.Flow

interface CommentService {
    suspend fun addComment(postId: Long, request: CommentRequest): CommentResponse
    fun getComments(postId: Long): Flow<CommentResponse>
}