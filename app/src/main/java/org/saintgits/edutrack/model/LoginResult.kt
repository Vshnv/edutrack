package org.saintgits.edutrack.model

sealed interface LoginResult {
    data class LoggedIn(val username: String, val jwtAuthToken: JwtAuthToken, val role: Role): LoginResult
    data class Error(val message: String): LoginResult
}