package org.saintgits.edutrack.model

interface CoursesRepository {
    suspend fun fetchCourse(courseIds: List<String>): ApiResult<Map<String, Course>>
}