package com.server.webfluxblog.domain.post.service

import com.server.webfluxblog.domain.post.domain.entity.PostEntity
import com.server.webfluxblog.domain.post.dto.request.CreatePostRequest
import kotlinx.coroutines.flow.Flow

interface PostService {
    suspend fun createPost(request: CreatePostRequest): PostEntity
    suspend fun findPostById(id: Long): PostEntity?
    fun getRecentPosts(): Flow<PostEntity>?
    fun searchPosts(query: String): Flow<PostEntity>
    suspend fun getTrendingPosts(): Flow<PostEntity>
    fun getFeed(): Flow<PostEntity>?
}