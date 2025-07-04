package com.server.webfluxblog.domain.post.service

import com.server.webfluxblog.domain.auth.error.PostError
import com.server.webfluxblog.domain.post.domain.entity.PostEntity
import com.server.webfluxblog.domain.post.dto.request.CreatePostRequest
import com.server.webfluxblog.domain.like.domain.repository.LikeRepository
import com.server.webfluxblog.domain.post.domain.repository.PostRepository
import com.server.webfluxblog.domain.post.dto.response.PostResponse
import com.server.webfluxblog.global.exception.CustomException
import com.server.webfluxblog.global.security.holder.SecurityHolder
import kotlinx.coroutines.flow.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PostService(
    private val postRepository: PostRepository,
    private val likeRepository: LikeRepository,
    private val securityHolder: SecurityHolder,
) {
    suspend fun createPost(request: CreatePostRequest) : PostResponse =
        postRepository.save(
            PostEntity(
                title = request.title,
                content = request.content,
                userId = securityHolder.getPrincipal()!!.id!!,
                likeCount = 0
            )
        ).toResponse()

    suspend fun findPostById(postId: Long) : PostResponse {
        val post = postRepository.findById(postId) ?: throw CustomException(PostError.POST_NOT_FOUND)
        return post.toResponse()
    }

    fun getRecentPosts(): Flow<PostResponse> {
        val posts = postRepository.findAllByOrderByCreatedAtDesc()
        return posts
            .map { it.toResponse() }
    }
    fun searchPosts(query: String): Flow<PostResponse> = postRepository.findByTitleContainingIgnoreCase(query).map { it.toResponse() }

    suspend fun getTrendingPosts(): Flow<PostResponse> {
        return likeRepository.findTop10ByOrderByLikeCountDesc()
            .mapNotNull { post -> postRepository.findById(post.postId) }
            .map { it.toResponse() }
    }

    fun getFeedPosts(): Flow<PostResponse> = postRepository.findAllByOrderByCreatedAtDesc().map { it.toResponse() }

    private fun PostEntity.toResponse() = PostResponse(
        id = id!!,
        title = title,
        content = content,
        userId = userId,
        likeCount = likeCount,
        createdAt = createdAt,
        updatedAt = updatedAt,
    )
}