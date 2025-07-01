package com.server.webfluxblog.domain.post.service.impl

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import com.server.webfluxblog.domain.announcement.repository.AnnouncementRepository
import com.server.webfluxblog.domain.post.domain.entity.PostEntity
import com.server.webfluxblog.domain.post.dto.request.CreatePostRequest
import com.server.webfluxblog.domain.post.repository.*
import com.server.webfluxblog.domain.comment.repository.CommentRepository
import com.server.webfluxblog.domain.like.repository.LikeRepository
import com.server.webfluxblog.domain.post.service.PostService
import com.server.webfluxblog.domain.user.repository.UserRepository
import com.server.webfluxblog.global.security.holder.SecurityHolder
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.reactive.awaitFirstOrNull
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration

@Service
@Transactional
class PostServiceImpl(
    private val postRepository: PostRepository,
    private val likeRepository: LikeRepository,
    private val securityHolder: SecurityHolder,
) : PostService {
    override suspend fun createPost(request: CreatePostRequest) = postRepository.save(
        PostEntity(
            title = request.title,
            content = request.content,
            userId = securityHolder.getPrincipal()!!.id!!,
            likeCount = 0
        )
    )

    override suspend fun findPostById(postId: Long) : PostEntity? = postRepository.findById(postId)

    override fun getRecentPosts(): Flow<PostEntity>? = postRepository.findAllByOrderByCreatedAtDesc()
    override fun searchPosts(query: String): Flow<PostEntity> = postRepository.findByTitleContainingIgnoreCase(query)

    override suspend fun getTrendingPosts(): Flow<PostEntity> {
        return likeRepository.findTop10ByOrderByLikeCountDesc()
            .mapNotNull { post -> postRepository.findById(post.postId) }
    }




    override fun getFeed(): Flow<PostEntity>? = postRepository.findAllByOrderByCreatedAtDesc()
}