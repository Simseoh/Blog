package com.server.webfluxblog.global.security.jwt.property

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.bind.ConstructorBinding
import org.springframework.stereotype.Component

@ConfigurationProperties(prefix = "jwt")
data class JwtProperties @ConstructorBinding constructor (
    val secretKey: String,
    val accessTokenExpiration: Long,
    val refreshTokenExpiration: Long
)