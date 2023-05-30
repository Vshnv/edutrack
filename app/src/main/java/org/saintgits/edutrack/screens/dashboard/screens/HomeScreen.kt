package org.saintgits.edutrack.screens.dashboard.screens

import android.os.Bundle
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
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
import androidx.lifecycle.viewmodel.compose.viewModel
import org.saintgits.edutrack.R
import org.saintgits.edutrack.model.AttendanceRecord
import org.saintgits.edutrack.model.Course
import org.saintgits.edutrack.model.Role
import org.saintgits.edutrack.model.StudentUser
import org.saintgits.edutrack.model.User
import org.saintgits.edutrack.screens.dashboard.DashboardScreen
import org.saintgits.edutrack.ui.theme.EdutrackTheme
import org.saintgits.edutrack.viewmodel.AttendanceViewModel
import org.saintgits.edutrack.viewmodel.result.LoadableState
import java.util.*
import kotlin.jvm.internal.Intrinsics
import kotlin.math.roundToInt

@Composable
fun HomeScreen(user: User) {
    val attendanceViewModel = viewModel(modelClass = AttendanceViewModel::class.java)
    val attendanceRecord by attendanceViewModel.attendanceRecord.observeAsState(LoadableState.Loading())
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
            TextField(label = { Text(text = "Name") },value = user.name, onValueChange = {}, enabled = false, colors = TextFieldDefaults.textFieldColors(disabledTextColor = Color.Black))
            TextField(label = { Text(text = "Role") },value = user.role.name, onValueChange = {}, enabled = false, colors = TextFieldDefaults.textFieldColors(disabledTextColor = Color.Black))
            TextField(label = { Text(text = "Email") },value = user.email, onValueChange = {}, enabled = false, colors = TextFieldDefaults.textFieldColors(disabledTextColor = Color.Black))
        }
        Spacer(modifier = Modifier.height(5.dp))
        if (user.role == Role.STUDENT) {
            when (val attRec = attendanceRecord) {
                is LoadableState.Error -> {
                    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                        Text(text = "Failed to load.")
                        Text(text = attRec.message)
                    }
                }
                is LoadableState.Loading -> {
                    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                        CircularProgressIndicator()
                    }
                }
                is LoadableState.Result -> {
                    LazyColumn(
                        Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(10.dp, 25.dp, 10.dp, 10.dp)
                            .clip(
                                RoundedCornerShape(10.dp)
                            )
                            .background(Color.Gray)) {
                        items(attRec.result.entries.toList()) {
                            AttendanceCard(it.key to it.value)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StudentAttendance() {
    
}


@Composable
fun AttendanceCard(data: Pair<Course, AttendanceRecord>) {
    val attendancePercent = remember(data) {
        val attended = data.second.attendance.count { it.value }.toFloat()
        val totalClassHeld = data.second.attendance.size
        attended / totalClassHeld
    }
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
                Text(text = data.first.code, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text(text = data.first.name)
                Spacer(modifier = Modifier.height(30.dp))
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "View")
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Box(modifier = Modifier.wrapContentSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(modifier = Modifier.size(100.dp), progress = attendancePercent)
                Text(text = "${(attendancePercent * 100).roundToInt()}", fontSize = 25.sp)
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


