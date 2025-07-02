package com.server.webfluxblog.domain.post.service

import com.server.webfluxblog.domain.post.domain.entity.PostEntity
import com.server.webfluxblog.domain.post.dto.request.CreatePostRequest
import com.server.webfluxblog.domain.post.dto.response.PostResponse
import kotlinx.coroutines.flow.Flow

interface PostService {
    suspend fun createPost(request: CreatePostRequest): PostResponse
    suspend fun findPostById(id: Long): PostResponse
    fun getRecentPosts(): Flow<PostResponse>
    fun searchPosts(query: String): Flow<PostResponse>
    suspend fun getTrendingPosts(): Flow<PostResponse>
    fun getFeed(): Flow<PostResponse>
}