package org.saintgits.edutrack.model

data class Course(
    val code: String,
    val name: String,
    val slotCodes: List<String>,
    val facultyUsername: String
)