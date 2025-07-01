package com.server.webfluxblog.domain.comment.controller

import com.server.webfluxblog.domain.comment.domain.entity.CommentEntity
import com.server.webfluxblog.domain.comment.dto.request.CommentRequest
import com.server.webfluxblog.domain.comment.service.CommentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.flow.Flow
import org.springframework.web.bind.annotation.*

@RestController
@Tag(name = "Comment")
class CommentController(
    val commentService: CommentService
) {
    @Operation(summary = "댓글 게시")
    @PostMapping("/comments")
    suspend fun addComment(@RequestBody request : CommentRequest): CommentEntity = commentService.addComment(request)

    @Operation(summary = "게시물 댓글 조회")
    @GetMapping("/posts/{postId}/comments")
    fun getComments(@PathVariable postId: Long): Flow<CommentEntity> = commentService.getComments(postId)
}