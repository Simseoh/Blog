package com.server.webfluxblog.domain.post.controller

import com.server.webfluxblog.domain.post.dto.request.CreatePostRequest
import com.server.webfluxblog.domain.post.service.PostService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/posts")
@Tag(name = "Post")
class PostController(
    private val postService: PostService
) {
    @Operation(summary = "게시물 게시")
    @PostMapping
    suspend fun createPost(@RequestBody request: CreatePostRequest) = postService.createPost(request)

    @Operation(summary = "게시물 상세 조회")
    @GetMapping("/{postId}")
    suspend fun getPostById(@PathVariable postId: Long) = postService.findPostById(postId)

    @Operation(summary = "게시물 전체 조회")
    @GetMapping("/recent")
    fun getRecentPosts() = postService.getRecentPosts()

    @Operation(summary = "트렌딩게시물 조회")
    @GetMapping("/trending")
    suspend fun getTrendingPosts() = postService.getTrendingPosts()

    @Operation(summary = "게시물 검색")
    @GetMapping("/search")
    fun searchPosts(@RequestParam query: String) = postService.searchPosts(query)

    @Operation(summary = "피드 조회")
    @GetMapping("/feed")
    fun getFeedPosts() = postService.getFeedPosts()
}