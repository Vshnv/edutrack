package org.saintgits.edutrack.screens.authentication

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import org.saintgits.edutrack.model.LoginResult
import org.saintgits.edutrack.screens.dashboard.DashboardActivity
import org.saintgits.edutrack.ui.theme.EdutrackTheme
import org.saintgits.edutrack.viewmodel.AuthViewModel

class AuthenticationActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val authViewModel = viewModel(modelClass = AuthViewModel::class.java)
            EdutrackTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    LoginScreen(login = { a, b ->
                        val result = authViewModel.login(a, b)
                        if (result) {
                            startActivity(Intent(this, DashboardActivity::class.java))
                        } else {
                            Toast.makeText(this, "Incorrect email/password", Toast.LENGTH_SHORT).show()
                        }
                        return@LoginScreen result
                    })
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    LoginScreen(login = { a,b -> false })
}