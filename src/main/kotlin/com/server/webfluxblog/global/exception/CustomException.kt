package com.server.webfluxblog.global.exception

class CustomException(
    val error: CustomError
) : RuntimeException(){
}