package com.server.webfluxblog.domain.like.service.impl

import com.server.webfluxblog.domain.like.domain.entity.LikeEntity
import com.server.webfluxblog.domain.like.domain.repository.LikeRepository
import com.server.webfluxblog.domain.like.service.LikeService
import com.server.webfluxblog.domain.notification.domain.entity.NotificationEntity
import com.server.webfluxblog.domain.notification.domain.repository.NotificationRepository
import com.server.webfluxblog.domain.post.domain.repository.PostRepository
import com.server.webfluxblog.global.security.holder.SecurityHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LikeServiceImpl(
    private val likeRepository: LikeRepository,
    private val postRepository: PostRepository,
    private val notificationRepository: NotificationRepository,
    private val securityHolder: SecurityHolder,
) : LikeService {
    override suspend fun toggleLike(postId: Long): Boolean {
        val userId : Long = securityHolder.getPrincipal()?.id!!
        val existingLike = likeRepository.findByPostIdAndUserId(postId, userId)
        val post = postRepository.findById(postId)
        return if (existingLike == null) {
            likeRepository.save(LikeEntity(postId = postId, userId = userId))
            postRepository.save(post?.addLike()!!)
            notifyUser(postId, "Your post received a like!")
            true
        } else {
            likeRepository.delete(existingLike)
            postRepository.save(post?.minusLike()!!)
            false
        }
    }

    private suspend fun notifyUser(postId: Long, message: String) {
        val post = postRepository.findById(postId) ?: return
        notificationRepository.save(NotificationEntity(userId = post.userId, message = message))
    }
}