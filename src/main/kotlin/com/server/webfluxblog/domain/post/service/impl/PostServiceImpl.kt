package com.server.webfluxblog.domain.post.service.impl

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
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration

@Service
@Transactional
class PostServiceImpl(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
    private val likeRepository: LikeRepository,
    private val announcementRepository: AnnouncementRepository,
    private val userRepository: UserRepository,
    private val redisTemplate: ReactiveRedisTemplate<String, String>,
    private val securityHolder: SecurityHolder
) : PostService {
    override suspend fun createPost(request: CreatePostRequest) = postRepository.save(
        PostEntity(
            title = request.title,
            content = request.content,
            userId = securityHolder.getPrincipal()!!.id!!
        )
    )
    override fun getRecentPosts(): Flow<PostEntity>? = postRepository.findAllByOrderByCreatedAtDesc()
    override fun searchPosts(query: String): Flow<PostEntity> = postRepository.findByTitleContainingIgnoreCase(query)

    override suspend fun getTrendingPosts(): Flow<PostEntity> {
        val cached = redisTemplate.opsForValue().get("trending_posts")
        return if (cached != null) {
            likeRepository.findTop10ByOrderByLikeCountDesc()
                .map { postRepository.findById(it.postId)!! }
                .also { redisTemplate.opsForValue().set("trending_posts", "cached", Duration.ofHours(1)) }
        } else {
            likeRepository.findTop10ByOrderByLikeCountDesc()
                .map { postRepository.findById(it.postId)!! }
        }
    }

    override fun getFeed(userId: Long): Flow<PostEntity>? = postRepository.findAllByOrderByCreatedAtDesc()
}