package org.saintgits.edutrack.screens.authentication

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.saintgits.edutrack.model.LoginResult
import org.saintgits.edutrack.screens.dashboard.DashboardActivity

/*@Composable
fun LoginScreen(loginViewModel: LoginViewModel) {

}*/


@Composable
fun LoginScreen(login: suspend (username: String, password: String) -> Boolean) {
    val (isLoggingIn, setLoggingIn) = remember { mutableStateOf(false) }
    val (username, setUsername) = rememberSaveable { mutableStateOf("") }
    val (password, setPassword) = rememberSaveable { mutableStateOf("") }
    val (isPasswordVisible, setPasswordVisible) = rememberSaveable { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Welcome to EduTrack!", fontWeight = FontWeight.ExtraBold, fontSize = 18.sp)
        Text(text = "Login", fontWeight = FontWeight.ExtraBold, fontSize = 32.sp)
        Spacer(modifier = Modifier.height(10.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp),
            placeholder = {
                Text(text = "Username")
            },
            value = username,
            onValueChange = setUsername
        )
        Spacer(modifier = Modifier.height(5.dp))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp, 0.dp),
            placeholder = {
                Text(text = "Password")
            },
            value = password,
            onValueChange = setPassword,
            visualTransformation = if (isPasswordVisible)
                VisualTransformation.None
            else
                PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (isPasswordVisible)
                    Icons.Filled.Visibility
                else
                    Icons.Filled.VisibilityOff
                val description = if (isPasswordVisible) "Hide password" else "Show password"

                IconButton(onClick = {setPasswordVisible(!isPasswordVisible)}){
                    Icon(imageVector  = image, description)
                }
            }
        )
        Spacer(modifier = Modifier.height(10.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .padding(10.dp, 0.dp),
            enabled = !isLoggingIn,
            onClick = {
                setLoggingIn(true)
                coroutineScope.launch {
                    delay(1500)
                    setLoggingIn(false)
                    withContext(Dispatchers.Main) {
                        context.startActivity(Intent(context, DashboardActivity::class.java))
                    }
                }
            }
        ) {
            if (isLoggingIn) {
                CircularProgressIndicator()
            } else {
                Text(text = "Login")
            }
        }
    }
}
