package org.saintgits.edutrack.screens.dashboard.screens

import android.os.Bundle
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.saintgits.edutrack.R
import org.saintgits.edutrack.model.Role
import org.saintgits.edutrack.model.User
import org.saintgits.edutrack.screens.dashboard.DashboardScreen
import org.saintgits.edutrack.ui.theme.EdutrackTheme
import java.util.*

@Composable
fun HomeScreen(user: User) {
    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.height(50.dp))
        Image(
            modifier = Modifier
                .size(200.dp)
                .padding(10.dp)
                .clip(CircleShape),
            painter = painterResource(id = R.drawable.avatar_default), contentDescription = "Avatar"
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(text = user.username, fontSize = 40.sp, fontWeight = FontWeight.Light)
        Spacer(modifier = Modifier.height(20.dp))
        when (user.role) {
            Role.STUDENT -> {
                LinearProgressIndicator(progress = 0.75f)
                Spacer(modifier = Modifier.height(5.dp))
                Text(text = "Attendance", fontSize = 15.sp, fontWeight = FontWeight.Light)
                Spacer(modifier = Modifier.height(30.dp))
            }
            else -> {

            }
        }

        Column {
            Text(
                text = "Name: ${user.name}",
                fontSize = 18.sp
            )
            Text(
                text = "Role: ${user.role.name.lowercase().replaceFirstChar { it.uppercase() }}",
                fontSize = 18.sp
            )
            Text(
                text = "DOB: ${user.birthDate}",
                fontSize = 18.sp
            )
        }

    }

}

@Preview(showBackground = true, name = "Home")
@Composable
fun HomePreview() {
    HomeScreen(User(
        username = "mals",
        name = "Malavika",
        birthDate = Date(),
        role = Role.STUDENT
    ))
}


