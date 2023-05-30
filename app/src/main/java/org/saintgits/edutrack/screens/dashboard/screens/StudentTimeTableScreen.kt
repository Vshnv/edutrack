package org.saintgits.edutrack.screens.dashboard.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.saintgits.edutrack.model.Course
import java.util.Date
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StudentTimeTableScreen(requestTimeTable: suspend (Date) -> List<Course>) {
    var date by remember { mutableStateOf(Date()) }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(modifier = Modifier.clickable {  }, label = { Text(text = "Date") },value = "${date.date}/${date.month}/${date.year}}", onValueChange = {}, trailingIcon = {
            Text("Edit")
        })

    }
}

@Composable
fun TimeTableCard() {
    Card(
        Modifier
            .fillMaxWidth()
            .height(100.dp)
            .padding(10.dp)) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(Color.White), verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(text = "CSE1002", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text(text = "Computer Architecture")
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(horizontalAlignment = Alignment.End) {
                Text(text = "09:30-05:20")
            }
        }
    }
}

@Preview
@Composable
fun TimeTableCardPreview() {
    TimeTableCard()
}


@Preview
@Composable
fun StudentTimeTableScreenPreview() {
    Surface {
        StudentTimeTableScreen(requestTimeTable = { emptyList() })
    }
}