package com.server.webfluxblog.global.security.jwt.handle

import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.server.authorization.ServerAccessDeniedHandler
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class CustomAuthenticationDenyHandle : ServerAccessDeniedHandler {

    override fun handle(exchange: ServerWebExchange, denied: AccessDeniedException): Mono<Void> {
        val response = exchange.response
        response.statusCode = HttpStatus.FORBIDDEN
        response.headers.contentType = MediaType.APPLICATION_JSON

        val jsonBody = """
            {
                "status": 403,
                "error": "Forbidden",
                "message": "Low Security Level",
                "path": "${exchange.request.uri.path}"
            }
        """.trimIndent()

        val buffer = response.bufferFactory().wrap(jsonBody.toByteArray(Charsets.UTF_8))
        return response.writeWith(Mono.just(buffer))
    }
}
