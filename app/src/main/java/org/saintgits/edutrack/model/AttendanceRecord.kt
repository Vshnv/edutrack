package org.saintgits.edutrack.model

import org.saintgits.edutrack.model.Course

data class AttendanceRecord(
    val courseId: String,
    val attendance: Map<String, Boolean>
)