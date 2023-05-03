package org.saintgits.edutrack.screens.dashboard

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.graphics.vector.ImageVector
import org.saintgits.edutrack.model.Role

enum class Screen(val title: String, val icon: ImageVector, val route: String, val roles: List<Role>) {
    Home("Home", Icons.Filled.Home, "/home", listOf(Role.STUDENT, Role.ADMIN, Role.FACULTY)),
    TimeTableStudent("Time Table", Icons.Filled.TableChart, "/students/time-table", listOf(Role.STUDENT)),
    TimeTableFaculty("Time Table", Icons.Filled.TableChart, "/faculty/time-table", listOf(Role.FACULTY)),
    Attendance("Attendance", Icons.Filled.Tag, "/attendance", listOf(Role.STUDENT)),
    AssignmentsStudent("Assignments", Icons.Filled.Book, "/students/assignments", listOf(Role.STUDENT)),
    AssignmentsFaculty("Assignments", Icons.Filled.Book, "/faculty/assignments", listOf(Role.FACULTY)),
    Performance("Performance", Icons.Filled.Scoreboard, "/performance", listOf(Role.STUDENT)),
    PostGrade("Post Grades", Icons.Filled.Scoreboard, "/post-grades", listOf(Role.FACULTY)),
    CreateUser("Create User", Icons.Filled.Create, "/create-user", listOf(Role.ADMIN)),
}