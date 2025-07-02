package com.server.webfluxblog.domain.comment.service.impl

import com.server.webfluxblog.domain.comment.domain.entity.CommentEntity
import com.server.webfluxblog.domain.comment.dto.request.CommentRequest
import com.server.webfluxblog.domain.comment.repository.CommentRepository
import com.server.webfluxblog.domain.comment.service.CommentService
import com.server.webfluxblog.domain.post.repository.PostRepository
import com.server.webfluxblog.global.security.holder.SecurityHolder
import kotlinx.coroutines.flow.Flow
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class CommentServiceImpl(
    private val commentRepository: CommentRepository,
    private val securityHolder: SecurityHolder
) : CommentService {
    override suspend fun addComment(postId: Long, request: CommentRequest): CommentEntity {
        var comment: CommentEntity = CommentEntity(
            postId = postId,
            userId = securityHolder.getPrincipal()?.id!!,
            content = request.content
        )
        return commentRepository.save(comment)
    }
    override fun getComments(postId: Long): Flow<CommentEntity> = commentRepository.findByPostId(postId)
}