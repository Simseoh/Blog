package com.server.webfluxblog.global.exception

import org.springframework.http.ResponseEntity

class ExceptionResponse(
    var status: Int,
    var code: String,
    var message: String
)
{
    constructor(
        error: CustomError
    ) : this(
        status = error.status.value(),
        code = (error as Enum<*>).name,
        message = error.message
    )

    companion object {
        fun of(error: CustomError) : ResponseEntity<ExceptionResponse> {
            return ResponseEntity.status(error.status).body(ExceptionResponse(error))
        }
    }
}