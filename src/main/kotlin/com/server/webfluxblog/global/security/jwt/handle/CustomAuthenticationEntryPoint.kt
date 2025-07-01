package com.server.webfluxblog.global.security.jwt.handle

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.web.server.ServerAuthenticationEntryPoint
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class CustomAuthenticationEntryPoint : ServerAuthenticationEntryPoint {

    override fun commence(exchange: ServerWebExchange, ex: org.springframework.security.core.AuthenticationException): Mono<Void> {
        val response = exchange.response
        response.statusCode = HttpStatus.UNAUTHORIZED
        response.headers.contentType = MediaType.APPLICATION_JSON

        val buffer = response.bufferFactory().wrap(
            """
            {
                "status": 401,
                "error": "Unauthorized",
                "message": "Need Account",
                "path": "${exchange.request.uri.path}"
            }
            """.trimIndent().toByteArray(Charsets.UTF_8)
        )

        return response.writeWith(Mono.just(buffer))
    }
}
