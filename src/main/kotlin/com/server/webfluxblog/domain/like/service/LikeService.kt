package com.server.webfluxblog.domain.like.service

import com.server.webfluxblog.domain.like.domain.entity.LikeEntity
import com.server.webfluxblog.domain.like.domain.repository.LikeRepository
import com.server.webfluxblog.domain.notification.domain.entity.NotificationEntity
import com.server.webfluxblog.domain.notification.domain.repository.NotificationRepository
import com.server.webfluxblog.domain.post.domain.repository.PostRepository
import com.server.webfluxblog.global.security.holder.SecurityHolder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class LikeService(
    private val likeRepository: LikeRepository,
    private val postRepository: PostRepository,
    private val notificationRepository: NotificationRepository,
    private val securityHolder: SecurityHolder,
) {
    suspend fun toggleLike(postId: Long): Boolean {
        val userId : Long = securityHolder.getPrincipal()?.id!!
        val existingLike = likeRepository.findByPostIdAndUserId(postId, userId)
        val post = postRepository.findById(postId)!!
        return if (existingLike == null) {
            likeRepository.save(LikeEntity(postId = postId, userId = userId))
            notifyUser(postId, "Your post received a like!")
            true
        } else {
            likeRepository.delete(existingLike)
            false
        }
    }

    suspend fun notifyUser(postId: Long, message: String) {
        val post = postRepository.findById(postId)!!
        notificationRepository.save(NotificationEntity(userId = post.userId, message = message))
    }
}