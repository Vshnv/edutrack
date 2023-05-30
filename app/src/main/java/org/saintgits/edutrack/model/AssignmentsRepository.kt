package org.saintgits.edutrack.model

interface AssignmentsRepository {
    suspend fun fetchAssignments(courseCodes: List<String>): ApiResult<List<Assignment>>
}