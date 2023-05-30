package org.saintgits.edutrack.model

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.coroutineScope
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseAttendanceRecordsRepository(private val firestore: FirebaseFirestore): AttendanceRecordsRepository {
    override suspend fun fetchAttendanceRecords(userId: String): ApiResult<List<AttendanceRecord>> = suspendCoroutine { continuation ->
        firestore.collection("users").document(userId).collection("attendance").addSnapshotListener { value, error ->
            if (error != null) {
                continuation.resume(ApiResult.Error(error))
                return@addSnapshotListener
            }
            val mappedCourses = value?.documents?.map {
                AttendanceRecord(it.id, (it.data?.map { it.key to (it.value as Boolean) }?.toMap() ?: emptyMap()))
            }
            continuation.resume(ApiResult.Success(mappedCourses ?: emptyList()))
        }
    }
}