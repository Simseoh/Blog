package com.server.webfluxblog.domain.announcement.domain.repository

import com.server.webfluxblog.domain.announcement.domain.entity.AnnouncementEntity
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AnnouncementRepository : CoroutineCrudRepository<AnnouncementEntity, Long> {
    fun findAllByOrderByCreatedAtDesc(): Flow<AnnouncementEntity>?
}
