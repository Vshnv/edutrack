package org.saintgits.edutrack.model

data class Course(
    val code: String,
    val name: String,
    val professor: String,
    val slot: List<String>,
    val totalClasses: Long
)
