package org.saintgits.edutrack.model

interface UserRepository {
    suspend fun fetchUser(userId: String): ApiResult<User>
}