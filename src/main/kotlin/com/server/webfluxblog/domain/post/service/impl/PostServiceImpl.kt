package com.server.webfluxblog.domain.post.service.impl

import com.server.webfluxblog.domain.auth.error.PostError
import com.server.webfluxblog.domain.post.domain.entity.PostEntity
import com.server.webfluxblog.domain.post.dto.request.CreatePostRequest
import com.server.webfluxblog.domain.like.domain.repository.LikeRepository
import com.server.webfluxblog.domain.post.domain.repository.PostRepository
import com.server.webfluxblog.domain.post.dto.response.PostResponse
import com.server.webfluxblog.domain.post.service.PostService
import com.server.webfluxblog.global.exception.CustomException
import com.server.webfluxblog.global.security.holder.SecurityHolder
import kotlinx.coroutines.flow.*
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val likeRepository: LikeRepository,
    private val securityHolder: SecurityHolder,
) : PostService {
    override suspend fun createPost(request: CreatePostRequest) : PostResponse =
        postRepository.save(
            PostEntity(
                title = request.title,
                content = request.content,
                userId = securityHolder.getPrincipal()!!.id!!,
                likeCount = 0
            )
        ).toResponse()

    override suspend fun findPostById(postId: Long) : PostResponse {
        val post = postRepository.findById(postId) ?: throw CustomException(PostError.POST_NOT_FOUND)
        return post.toResponse()
    }

    override fun getRecentPosts(): Flow<PostResponse> {
        val posts = postRepository.findAllByOrderByCreatedAtDesc()
        return posts
            .map { it.toResponse() }
    }
    override fun searchPosts(query: String): Flow<PostResponse> = postRepository.findByTitleContainingIgnoreCase(query).map { it.toResponse() }

    override suspend fun getTrendingPosts(): Flow<PostResponse> {
        return likeRepository.findTop10ByOrderByLikeCountDesc()
            .mapNotNull { post -> postRepository.findById(post.postId) }
            .map { it.toResponse() }
    }

    override fun getFeed(): Flow<PostResponse> = postRepository.findAllByOrderByCreatedAtDesc().map { it.toResponse() }

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