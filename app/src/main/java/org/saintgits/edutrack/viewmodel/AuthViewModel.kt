package org.saintgits.edutrack.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class AuthViewModel: ViewModel() {
    suspend fun login(email: String, password: String): Boolean = suspendCoroutine {continuation ->
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            continuation.resume(task.isSuccessful)
        }
    }

    suspend fun signup(email: String, password: String): Boolean = suspendCoroutine {continuation ->
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            continuation.resume(task.isSuccessful)
        }
    }
}