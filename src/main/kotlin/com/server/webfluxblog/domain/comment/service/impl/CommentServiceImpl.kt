package com.server.webfluxblog.domain.comment.service.impl

import com.server.webfluxblog.domain.comment.domain.entity.CommentEntity
import com.server.webfluxblog.domain.comment.dto.request.CommentRequest
import com.server.webfluxblog.domain.comment.dto.response.CommentResponse
import com.server.webfluxblog.domain.comment.domain.repository.CommentRepository
import com.server.webfluxblog.domain.comment.service.CommentService
import com.server.webfluxblog.global.security.holder.SecurityHolder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val securityHolder: SecurityHolder
) : CommentService {
    override suspend fun addComment(postId: Long, request: CommentRequest): CommentResponse {
        var comment: CommentEntity = CommentEntity(
            postId = postId,
            userId = securityHolder.getPrincipal()?.id!!,
            content = request.content
        )
        return commentRepository.save(comment).toResponse()
    }
    override fun getComments(postId: Long): Flow<CommentResponse> = commentRepository.findByPostId(postId).map { it.toResponse() }

    private fun CommentEntity.toResponse(): CommentResponse {
        return CommentResponse(
            id = id!!,
            postId = postId,
            userId = userId,
            content = content,
            createdAt = createdAt
        )
    }
}