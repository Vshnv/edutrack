package org.saintgits.edutrack.model

enum class Role {
    STUDENT,
    FACULTY,
    ADMIN
}

fun String.matchRole(): Role? {
    return when {
        this.equals("student", true) -> {
            Role.STUDENT
        }
        this.equals("faculty", true) -> {
            Role.FACULTY
        }
        this.equals("admin", true) -> {
            Role.ADMIN
        }
        else -> null
    }
}