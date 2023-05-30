package org.saintgits.edutrack.model

import com.google.firebase.firestore.FirebaseFirestore
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseCoursesRepository(private val firestore: FirebaseFirestore): CoursesRepository {
    override suspend fun fetchCourse(courseIds: List<String>): ApiResult<Map<String, Course>> = suspendCoroutine { continuation ->
        if (courseIds.isEmpty()) {
            continuation.resume(ApiResult.Success(emptyMap()))
            return@suspendCoroutine
        }
        firestore.collection("courses").whereIn("code", courseIds).addSnapshotListener { value, error ->
            if (error != null) {
                continuation.resume(ApiResult.Error(error))
                return@addSnapshotListener
            }
            continuation.resume(ApiResult.Success(
                value?.documents?.map {
                    val code = it.getString("code") ?: ""
                    val name = it.getString("name") ?: ""
                    val professor = it.getString("professor") ?: ""
                    val totalClasses = it.getLong("total_classes") ?: 0
                    val slots = it.get("slot") as? List<String> ?: emptyList()
                    Course(code, name, professor, slots, totalClasses)
                }?.associateBy { it.code } ?: emptyMap()
            ))
        }
    }
}