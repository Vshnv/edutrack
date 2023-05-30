package org.saintgits.edutrack.model

interface AttendanceRecordsRepository {
    suspend fun fetchAttendanceRecords(userId: String) : ApiResult<List<AttendanceRecord>>
}