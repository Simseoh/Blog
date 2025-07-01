package com.server.webfluxblog.global.exception

import org.springframework.http.HttpStatus

interface CustomError {
    val status: HttpStatus
    val message: String
}