package org.saintgits.edutrack.model

data class JwtAuthToken(
    val accessToken: String,
    val refreshToken: String
)