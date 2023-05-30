package org.saintgits.edutrack.screens.dashboard.screens

import android.os.Bundle
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldColors
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.capitalize
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.saintgits.edutrack.R
import org.saintgits.edutrack.model.Role
import org.saintgits.edutrack.model.StudentUser
import org.saintgits.edutrack.model.User
import org.saintgits.edutrack.screens.dashboard.DashboardScreen
import org.saintgits.edutrack.ui.theme.EdutrackTheme
import java.util.*
import kotlin.jvm.internal.Intrinsics

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
        Text(text = user.name, fontSize = 40.sp, fontWeight = FontWeight.Light)
        Spacer(modifier = Modifier.height(20.dp))
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            TextField(label = { Text(text = "Name") },value = "Malavika", onValueChange = {}, enabled = false, colors = TextFieldDefaults.textFieldColors(disabledTextColor = Color.Black))
            TextField(label = { Text(text = "Role") },value = "Student", onValueChange = {}, enabled = false, colors = TextFieldDefaults.textFieldColors(disabledTextColor = Color.Black))
            TextField(label = { Text(text = "Email") },value = "malavika@gmail.com", onValueChange = {}, enabled = false, colors = TextFieldDefaults.textFieldColors(disabledTextColor = Color.Black))
        }
        Spacer(modifier = Modifier.height(5.dp))
        if (user.role == Role.STUDENT) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .padding(10.dp, 25.dp, 10.dp, 10.dp)
                    .clip(
                        RoundedCornerShape(10.dp)
                    )
                    .background(Color.Gray)) {
                AttendanceCard()
            }
        }
    }
}

@Composable
fun StudentAttendance() {
    
}


@Composable
fun AttendanceCard() {
    Card(
        Modifier
            .fillMaxWidth()
            .padding(10.dp)) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(Color.White), verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(text = "CSE1002", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text(text = "Computer Architecture")
                Spacer(modifier = Modifier.height(30.dp))
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "View")
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(modifier = Modifier.wrapContentSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(100.dp), progress = 0.75f)
                Text(text = "75", fontSize = 25.sp)
            }
        }
    }
}

@Preview(showBackground = true, name = "Home")
@Composable
fun HomePreview() {
    HomeScreen(StudentUser(
        name = "mals",
        email = "Malavika",
        role = Role.STUDENT,
        courseCodes = listOf()
    ))
}


