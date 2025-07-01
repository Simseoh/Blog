package com.server.webfluxblog.domain.blog.service

import com.server.webfluxblog.domain.announcement.domain.entity.AnnouncementEntity
import com.server.webfluxblog.domain.announcement.repository.AnnouncementRepository
import com.server.webfluxblog.domain.blog.domain.entity.PostEntity
import com.server.webfluxblog.domain.blog.dto.request.PostPostRequest
import com.server.webfluxblog.domain.blog.repository.*
import com.server.webfluxblog.domain.comment.domain.entity.CommentEntity
import com.server.webfluxblog.domain.comment.repository.CommentRepository
import com.server.webfluxblog.domain.like.domain.entity.LikeEntity
import com.server.webfluxblog.domain.like.repository.LikeRepository
import com.server.webfluxblog.domain.notification.domain.entity.NotificationEntity
import com.server.webfluxblog.domain.notification.repository.NotificationRepository
import com.server.webfluxblog.domain.user.repository.UserRepository
import com.server.webfluxblog.global.security.holder.SecurityHolder
import kotlinx.coroutines.flow.*
import org.springframework.data.redis.core.ReactiveRedisTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.util.*

@Service
@Transactional
class BlogService(
    private val commentRepository: CommentRepository,
    private val postRepository: PostRepository,
    private val likeRepository: LikeRepository,
    private val announcementRepository: AnnouncementRepository,
    private val notificationRepository: NotificationRepository,
    private val userRepository: UserRepository,
    private val redisTemplate: ReactiveRedisTemplate<String, String>,
    private val securityHolder: SecurityHolder
) {
    suspend fun createPost(request: PostPostRequest) = postRepository.save(
        PostEntity(
            title = request.title,
            content = request.content,
            userId = securityHolder.getPrincipal()!!.id!!
        )
    )
    fun getRecentPosts(): Flow<PostEntity>? = postRepository.findAllByOrderByCreatedAtDesc()
    fun searchPosts(query: String): Flow<PostEntity> = postRepository.findByTitleContainingIgnoreCase(query)

    suspend fun getTrendingPosts(): Flow<PostEntity> {
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

    suspend fun addComment(comment: CommentEntity): CommentEntity = commentRepository.save(comment)
    fun getComments(postId: Long): Flow<CommentEntity> = commentRepository.findByPostId(postId)

    suspend fun toggleLike(postId: Long, userId: Long): Boolean {
        val existingLike = likeRepository.findByPostIdAndUserId(postId, userId)
        return if (existingLike == null) {
            likeRepository.save(LikeEntity(postId = postId, userId = userId))
            notifyUser(postId, userId, "Your post received a like!")
            true
        } else {
            likeRepository.delete(existingLike)
            false
        }
    }

    suspend fun createAnnouncement(announcement: AnnouncementEntity): AnnouncementEntity = announcementRepository.save(announcement)
    fun getAnnouncements(): Flow<AnnouncementEntity>? = announcementRepository.findAllByOrderByCreatedAtDesc()

    private suspend fun notifyUser(postId: Long, userId: Long, message: String) {
        val post = postRepository.findById(postId) ?: return
        notificationRepository.save(NotificationEntity(userId = post.userId, message = message))
    }
    fun getNotifications(userId: Long): Flow<NotificationEntity>? = notificationRepository.findByUserIdAndIsReadFalse(userId)

    fun getFeed(userId: Long): Flow<PostEntity>? = postRepository.findAllByOrderByCreatedAtDesc()
}