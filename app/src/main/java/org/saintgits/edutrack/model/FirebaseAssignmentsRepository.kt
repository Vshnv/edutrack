package org.saintgits.edutrack.model

import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseAssignmentsRepository(private val firestore: FirebaseFirestore): AssignmentsRepository {
    override suspend fun fetchAssignments(courseCodes: List<String>): ApiResult<List<Assignment>> = suspendCoroutine{ continuation ->
        firestore.collection("assignments").whereIn("course", courseCodes).get().addOnCompleteListener { value ->
            val result = value.result
            val mappedCourses = result?.documents?.map {
                Assignment(
                    course = it.getString("course") ?: "",
                    due = it.getDate("due_by") ?: Date(),
                    instructions = it.getString("instructions") ?: "",
                    title = it.getString("title") ?: ""
                )
            }
            continuation.resume(ApiResult.Success(mappedCourses ?: emptyList()))


        }.addOnFailureListener {
            continuation.resume(ApiResult.Error(it))
        }
    }
}