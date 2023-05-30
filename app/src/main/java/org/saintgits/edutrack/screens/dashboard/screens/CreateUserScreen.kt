package org.saintgits.edutrack.screens.dashboard.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.saintgits.edutrack.model.Role
import org.saintgits.edutrack.model.matchRole

@Composable
fun CreateUserScreen() {
    val coroutineScope = rememberCoroutineScope()
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        var name by remember {
            mutableStateOf("")
        }
        var email by remember {
            mutableStateOf("")
        }
        var password by remember {
            mutableStateOf("")
        }
        var role by remember {
            mutableStateOf("")
        }
        val context = LocalContext.current
        TextField(label = { Text(text = "Name") },value = name, onValueChange = { name = it})
        TextField(label = { Text(text = "Email") },value = email, onValueChange = { email = it})
        TextField(label = { Text(text = "Password") },value = password, onValueChange = {password = it})
        TextField(label = { Text(text = "Role") },value = role, onValueChange = {role = it})
        Button(onClick = {
            if (name.isBlank() || email.isBlank() || password.isBlank()) {
                Toast.makeText(context, "Fill all details", Toast.LENGTH_SHORT).show()
                return@Button
            }
            val actualRole = role.matchRole()
            if (actualRole == null) {
                Toast.makeText(context, "Role must be Student, Admin or Faculty", Toast.LENGTH_SHORT).show()
                return@Button
            }
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                    val result = it.result
                val uid = result.user?.uid ?: return@addOnCompleteListener
                FirebaseFirestore.getInstance().collection("users").document(uid).set(mapOf(
                    "name" to name,
                    "email" to email,
                    "role" to role.lowercase()
                )).addOnCompleteListener {
                    coroutineScope.launch {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                        }
                    }
                }

            }.addOnFailureListener {
                coroutineScope.launch {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(context, "Failed to create acc", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }) {
            Text(text = "Create")
        }
    }
}