package com.server.webfluxblog.domain.comment.controller

import com.server.webfluxblog.domain.comment.dto.request.CommentRequest
import com.server.webfluxblog.domain.comment.service.CommentService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/posts/{postId}/comments")
@Tag(name = "Comment")
class CommentController(
    val commentService: CommentService
) {
    @Operation(summary = "댓글 게시")
    @PostMapping
    suspend fun addComment(@PathVariable postId: Long, @RequestBody request : CommentRequest) = commentService.addComment(postId, request)

    @Operation(summary = "게시물 댓글 조회")
    @GetMapping
    fun getComments(@PathVariable postId: Long) = commentService.getComments(postId)
}