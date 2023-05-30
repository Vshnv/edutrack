package org.saintgits.edutrack.model

import com.google.firebase.firestore.FirebaseFirestore
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FirebaseUserRepository(private val firestore: FirebaseFirestore): UserRepository {
    override suspend fun fetchUser(userId: String): ApiResult<User> = suspendCoroutine { continuation ->
        firestore.collection("users").document(userId).addSnapshotListener { value, error ->
            if (error != null || value == null) {
                continuation.resume(ApiResult.Error(error))
                return@addSnapshotListener
            }
            val name = value.getString("name")
            if (name == null) {
                continuation.resume(ApiResult.Error(RuntimeException("Corrupted User")))
                return@addSnapshotListener
            }
            val email = value.getString("email") ?: "N/A"
            val role = value.getString("role")?.matchRole() ?: Role.STUDENT
            val result = when (role) {
                Role.STUDENT -> {
                    val registeredCourses = value.get("registered_courses") as List<String>
                    StudentUser(
                        name,
                        email,
                        role,
                        registeredCourses
                    )
                }
                else -> {
                    StaffUser(
                        name,
                        email,
                        role
                    )
                }
            }
            continuation.resume(ApiResult.Success(result))
        }
    }
}