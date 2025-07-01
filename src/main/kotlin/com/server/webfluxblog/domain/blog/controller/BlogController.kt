package com.server.webfluxblog.domain.blog.controller

import com.server.webfluxblog.domain.announcement.domain.entity.AnnouncementEntity
import com.server.webfluxblog.domain.blog.domain.entity.PostEntity
import com.server.webfluxblog.domain.blog.dto.request.PostPostRequest
import com.server.webfluxblog.domain.blog.service.BlogService
import com.server.webfluxblog.domain.comment.domain.entity.CommentEntity
import com.server.webfluxblog.domain.notification.domain.entity.NotificationEntity
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import kotlinx.coroutines.flow.Flow
import org.springframework.web.bind.annotation.*
import java.security.Principal

@RestController
@RequestMapping("/api")
@Tag(name = "Blog")
class BlogController(private val blogService: BlogService) {

    @Operation(summary = "게시물 게시")
    @PostMapping("/posts")
    suspend fun createPost(@RequestBody request: PostPostRequest) = blogService.createPost(request)
    @Operation(summary = "게시물 전체 조회")
    @GetMapping("/posts/recent")
    fun getRecentPosts(): Flow<PostEntity>? = blogService.getRecentPosts()
    @Operation(summary = "트렌딩게시물 조회")
    @GetMapping("/posts/trending")
    suspend fun getTrendingPosts(): Flow<PostEntity> = blogService.getTrendingPosts()
    @Operation(summary = "게시물 검색")
    @GetMapping("/posts/search")
    fun searchPosts(@RequestParam query: String): Flow<PostEntity> = blogService.searchPosts(query)

    @Operation(summary = "댓글 게시")
    @PostMapping("/comments")
    suspend fun addComment(@RequestBody comment: CommentEntity): CommentEntity = blogService.addComment(comment)
    @Operation(summary = "게시물 댓글 조회")
    @GetMapping("/posts/{postId}/comments")
    fun getComments(@PathVariable postId: Long): Flow<CommentEntity> = blogService.getComments(postId)

    @Operation(summary = "게시물 좋아요 게시")
    @PostMapping("/posts/{postId}/like")
    suspend fun toggleLike(@PathVariable postId: Long, principal: Principal): Boolean =
        blogService.toggleLike(postId, principal.name.toLong())

    @Operation(summary = "공지사항 게시")
    @PostMapping("/announcements")
    suspend fun createAnnouncement(@RequestBody announcement: AnnouncementEntity): AnnouncementEntity =
        blogService.createAnnouncement(announcement)
    @Operation(summary = "공지사항 조회")
    @GetMapping("/announcements")
    fun getAnnouncements(): Flow<AnnouncementEntity>? = blogService.getAnnouncements()

    @Operation(summary = "알림 수신")
    @GetMapping("/notifications")
    fun getNotifications(principal: Principal): Flow<NotificationEntity>? =
        blogService.getNotifications(principal.name.toLong())

    @Operation(summary = "피드 조회")
    @GetMapping("/feed")
    fun getFeed(principal: Principal): Flow<PostEntity>? = blogService.getFeed(principal.name.toLong())
}