package org.saintgits.edutrack.screens.dashboard.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.saintgits.edutrack.model.Course
import org.saintgits.edutrack.utils.dayAsText
import java.text.SimpleDateFormat
import java.util.*

/*
val DATE_FORMAT = SimpleDateFormat.getDateInstance()
@Composable
fun TimeTableScreen() {
    TimeTable(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(), listOf(
            Course(
                code = "CSE3001",
                "Data Structures",
                listOf("M0", "W1", "F0"),
                "Prof"
            ),
            Course(
                code = "MAT1001",
                "Linear Algebra",
                listOf("TU0", "TH1", "W4"),
                "Prof"
            ),
            Course(
                code = "MAT1003",
                "Calculus",
                listOf("TU5", "F3", "M5"),
                "Prof"
            )
        )
            .flatMap { course -> course.slotCodes.map { slot -> slot to course } }
            .toMap(),
        startDate = run {
            val calendar = Calendar.getInstance()
            calendar.set(2023, 1, 1)
            calendar.time
        },
        endDate = run {
            val calendar = Calendar.getInstance()
            calendar.set(2023, 3, 15)
            calendar.time
        }
    )
}

@Preview(showBackground = true, name = "UserTimeTable")
@Composable
fun TimeTablePreview() {
    //Text(text = "Hi")
    TimeTable(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(), listOf(
            Course(
                code = "CSE3001",
                "Data Structures",
                listOf("M0", "W1", "F0"),
                "Prof"
            ),
            Course(
                code = "MAT1001",
                "Linear Algebra",
                listOf("TU0", "TH1", "W4"),
                "Prof"
            ),
            Course(
                code = "MAT1003",
                "Calculus",
                listOf("TU5", "F3", "M5"),
                "Prof"
            )
        )
            .flatMap { course -> course.slotCodes.map { slot -> slot to course } }
            .toMap(),
        startDate = run {
            val calendar = Calendar.getInstance()
            calendar.set(2023, 1, 1)
            calendar.time
        },
        endDate = run {
            val calendar = Calendar.getInstance()
            calendar.set(2023, 3, 15)
            calendar.time
        }
    )
}


@Composable
fun TimeTable(modifier: Modifier = Modifier, schedule: Map<String, Course>, startDate: Date, endDate: Date) {
    val days = remember {
        listOf(
            "Monday" to "M", "Tuesday" to "TU", "Wednesday" to "W", "Thursday" to "TH", "Friday" to "F"
        )
    }
    val dayKeyMap = days.toMap()
    val dates = remember(startDate, endDate) {
        buildList<Date> {
            val weekends = listOf(Calendar.SUNDAY, Calendar.SATURDAY, 0)
            val current = Calendar.getInstance()
            current.time = startDate
            add(current.time)
            val end = Calendar.getInstance()
            end.time = endDate
            while (current.before(end)) {
                current.add(Calendar.DATE, 1)
                val date = current.time
                if (date.day in weekends) {
                    continue
                }
                add(date)
            }
        }
    }
    listOf(
        "09:00AM - 09:50AM",
        "09:50AM - 10:40AM",
        "10:50AM - 11:40AM",
        "11:40AM - 12:30PM",
        "01:30PM - 02:20PM",
        "02:30PM - 03:10PM",
         "03:20PM - 04:10PM"
         )

    val horizontalScrollState = rememberScrollState()
    Column {
        Column(modifier = Modifier
            .wrapContentHeight()
            .horizontalScroll(horizontalScrollState)) {
            Row {
                HeaderCell {
                    Text(text = "", fontWeight = FontWeight.Bold)
                }
                HeaderCell{
                    Text(text = "09:00AM - 09:50AM", fontWeight = FontWeight.Bold)
                }
                HeaderCell{
                    Text(text = "09:50AM - 10:40AM", fontWeight = FontWeight.Bold)
                }
                HeaderCell{
                    Text(text = "10:50AM - 11:40AM", fontWeight = FontWeight.Bold)
                }
                HeaderCell{
                    Text(text = "11:40AM - 12:30PM", fontWeight = FontWeight.Bold)
                }
                HeaderCell{
                    Text(text = "01:30PM - 02:20PM", fontWeight = FontWeight.Bold)
                }
                HeaderCell{
                    Text(text = "02:30PM - 03:10PM", fontWeight = FontWeight.Bold)
                }
                HeaderCell{
                    Text(text = "03:20PM - 04:10PM", fontWeight = FontWeight.Bold)
                }
            }
        }
        LazyColumn(
            modifier =
            modifier
                .fillMaxSize()
                .horizontalScroll(horizontalScrollState),
        ) {
            items(dates) {date ->
                val day = date.dayAsText()
                val dayId = dayKeyMap[day] ?: "M"
                Row {
                    HeaderCell {
                        Text(text = DATE_FORMAT.format(date))
                        Text(text = day, fontWeight = FontWeight.Bold)
                    }
                    repeat(7) { idx ->
                        val slotCode = dayId + idx
                        val course = schedule[slotCode]
                        TimeTableCell(color = if (course == null) Color.White else Color.Green) {
                            if (course != null) {
                                Text(text = course.code)
                            }
                            Text(text = slotCode)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HeaderCell(text: String = "", content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .height(75.dp)
            .aspectRatio(16f / 9f)
            .border(BorderStroke(0.5.dp, Color.Black))
            .background(
                brush = Brush.radialGradient(
                    listOf(Color.LightGray, Color.Gray, Color.LightGray),
                    radius = 700f
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
    }
}

@Composable
fun TimeTableCell(color: Color, content: @Composable ColumnScope.() -> Unit) {
    Column(
        modifier = Modifier
            .height(75.dp)
            .aspectRatio(16f / 9f)
            .border(BorderStroke(0.5.dp, Color.Black))
            .background(color),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        content()
    }
}

*/
