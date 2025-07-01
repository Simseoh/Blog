package com.server.webfluxblog.domain.like.service

interface LikeService {
    suspend fun toggleLike(postId: Long): Boolean
}