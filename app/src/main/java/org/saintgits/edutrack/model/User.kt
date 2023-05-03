package org.saintgits.edutrack.model

import java.util.Date

data class User(
    val username: String,
    val name: String,
    val birthDate: Date,
    val role: Role
)