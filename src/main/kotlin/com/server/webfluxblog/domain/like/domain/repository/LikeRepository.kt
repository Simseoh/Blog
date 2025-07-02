package com.server.webfluxblog.domain.like.domain.repository

import com.server.webfluxblog.domain.like.domain.entity.LikeEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface LikeRepository : CoroutineCrudRepository<LikeEntity, Long> {
    suspend fun findByPostIdAndUserId(postId: Long, userId: Long): LikeEntity?
    @Query("SELECT post_id, COUNT(*) as like_count FROM likes GROUP BY post_id ORDER BY like_count DESC LIMIT 10")
    fun findTop10ByOrderByLikeCountDesc(): Flow<TrendingPost>
}

data class TrendingPost(val postId: Long, val likeCount: Long)
