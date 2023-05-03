package org.saintgits.edutrack.screens.dashboard.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import org.saintgits.edutrack.model.Course
import org.saintgits.edutrack.utils.dayAsText
import java.util.*

@Composable
fun AttendanceScreen() {
    Column(modifier = Modifier.fillMaxSize()) {

    }
}


@Composable
fun AttendanceTable(courses: List<Course>, startDate: Date, endDate: Date) {
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
    /*// day ->
    val coursesByDay = remember(days) {
        days.map { day ->
            val codes = (1..7).map {id -> day.second + id}
            courses.flatMap { course -> course.slotCodes.filter { codes.contains(it) }.map {  }  }
        }
    }
    val slots = remember(dates) {
        dates.flatMap { date ->
            val day = date.dayAsText()

        }
    }*/

}


data class AttendanceSlot(
    val date: Date,
    val slot: String,
    val course: Course
)
