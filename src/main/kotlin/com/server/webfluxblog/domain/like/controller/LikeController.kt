package com.server.webfluxblog.domain.like.controller

import com.server.webfluxblog.domain.like.service.LikeService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/posts/{postId}/likes")
@Tag(name = "Like")
class LikeController(
    private val likeService: LikeService
) {
    @Operation(summary = "게시물 좋아요 게시 / 삭제")
    @PostMapping
    suspend fun toggleLike(@PathVariable postId: Long) = likeService.toggleLike(postId)
}