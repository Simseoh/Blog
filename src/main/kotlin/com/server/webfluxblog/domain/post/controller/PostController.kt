package com.server.webfluxblog.domain.post.controller

import com.server.webfluxblog.domain.post.domain.entity.PostEntity
import com.server.webfluxblog.domain.post.dto.request.CreatePostRequest
import com.server.webfluxblog.domain.post.service.PostService
import com.server.webfluxblog.domain.post.service.impl.PostServiceImpl
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.flow.Flow
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/posts")
@Tag(name = "Post")
class PostController(private val postService: PostService) {

    @Operation(summary = "게시물 게시")
    @PostMapping
    suspend fun createPost(@RequestBody request: CreatePostRequest) = postService.createPost(request)

    @Operation(summary = "게시물 상세 조회")
    @GetMapping("/{postId}")
    suspend fun getPostById(@PathVariable postId: Long) = postService.findPostById(postId)

    @Operation(summary = "게시물 전체 조회")
    @GetMapping("/recent")
    fun getRecentPosts(): Flow<PostEntity>? = postService.getRecentPosts()
    @Operation(summary = "트렌딩게시물 조회")
    @GetMapping("/trending")
    suspend fun getTrendingPosts(): Flow<PostEntity> = postService.getTrendingPosts()
    @Operation(summary = "게시물 검색")
    @GetMapping("/search")
    fun searchPosts(@RequestParam query: String): Flow<PostEntity> = postService.searchPosts(query)

    @Operation(summary = "피드 조회")
    @GetMapping("/feed")
    fun getFeed(): Flow<PostEntity>? = postService.getFeed()
}