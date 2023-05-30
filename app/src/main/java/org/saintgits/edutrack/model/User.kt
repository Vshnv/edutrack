package org.saintgits.edutrack.model

import java.util.Date

interface User {
    val name: String
    val email: String
    val role: Role
}

data class StaffUser(
    override val name: String,
    override val email: String,
    override val role: Role
): User

data class StudentUser(
    override val name: String,
    override val email: String,
    override val role: Role,
    val courseCodes: List<String>
): User