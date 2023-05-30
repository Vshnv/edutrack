package org.saintgits.edutrack.screens.dashboard.screens

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import org.saintgits.edutrack.model.User
import org.saintgits.edutrack.viewmodel.StudentTimeTableViewModel
import org.saintgits.edutrack.viewmodel.TimeTableElement
import org.saintgits.edutrack.viewmodel.result.LoadableState
import java.util.Calendar

@Composable
fun StudentTimeTableScreen(user: User) {
    val studentTimeTableViewModel = viewModel<StudentTimeTableViewModel>(factory = StudentTimeTableViewModel.Factory(user))
    val dataState by studentTimeTableViewModel.coursesForDay.observeAsState(initial = LoadableState.Loading())
    val calender by studentTimeTableViewModel.activeDate.observeAsState()
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val mDatePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            val calendarUpdate = Calendar.getInstance()
            calendarUpdate.set(Calendar.DATE, mDayOfMonth)
            calendarUpdate.set(Calendar.MONTH, mMonth)
            calendarUpdate.set(Calendar.YEAR, mYear)
            coroutineScope.launch {
                studentTimeTableViewModel.updateDate(calendarUpdate)
            }
        }, calender?.get(Calendar.YEAR) ?: 0, calender?.get(Calendar.MONTH) ?: 0, calender?.get(Calendar.DATE) ?: 0
    )
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(10.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        TextField(label = { Text(text = "Date") },value = "${calender?.get(Calendar.DATE)}/${
            calender?.get(
                Calendar.MONTH
            ) ?: (0 + 1)
        }/${calender?.get(Calendar.YEAR)}", onValueChange = {}, trailingIcon = {
            Text(modifier = Modifier.clickable {
                mDatePickerDialog.show()
            }, text = "Edit")
        })
        when (val data = dataState) {
            is LoadableState.Error -> {
                Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Text(text = "Failed to load.")
                    Text(text = data.message)
                }
            }
            is LoadableState.Loading -> {
                Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    CircularProgressIndicator()
                }
            }
            is LoadableState.Result -> {
                LazyColumn(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    items(data.result) {
                        TimeTableCard(it)
                    }
                }
            }
        }
    }
}

@Composable
fun TimeTableCard(course: TimeTableElement) {
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
                Text(text = course.course.code, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text(text = course.course.name)
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(horizontalAlignment = Alignment.End) {
                Text(text = course.time)
            }
        }
    }
}

@Preview
@Composable
fun TimeTableCardPreview() {
    LazyColumn(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        items((1..5).map { Course(
            "CSE1001",
            "Computer Architecture",
            "ProfA",
            listOf("M1"),
            50
        ) }) {
            TimeTableCard(TimeTableElement(0, it, "M1", ""))
        }
    }
}


@Preview
@Composable
fun StudentTimeTableScreenPreview() {
   /* Surface {
        StudentTimeTableScreen(requestTimeTable = { emptyList() })
    }*/
}