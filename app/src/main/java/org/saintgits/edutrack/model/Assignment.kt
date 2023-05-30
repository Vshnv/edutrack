package org.saintgits.edutrack.model

import java.util.Date

data class Assignment(
    val course: String,
    val due: Date,
    val instructions: String,
    val title: String
)