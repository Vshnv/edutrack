package org.saintgits.edutrack.screens.dashboard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import kotlinx.coroutines.launch
import org.saintgits.edutrack.model.Role
import org.saintgits.edutrack.model.User
import org.saintgits.edutrack.screens.dashboard.screens.HomeScreen
import org.saintgits.edutrack.screens.dashboard.screens.TimeTableScreen
import org.saintgits.edutrack.ui.theme.EdutrackTheme
import java.util.*

class DashboardActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EdutrackTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DashboardScreen("User", Role.STUDENT)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen("User", Role.STUDENT)
}

@Preview(showBackground = true, name = "NavDrawer")
@Composable
fun NavDrawerPreview() {
    val role = Role.STUDENT
    val screens = remember(role) {
        Screen.values().filter { role in it.roles }
    }
    NavDrawer("User", screens, {})
}

@Preview(showBackground = true, name = "Home")
@Composable
fun HomePreview() {
    val role = Role.STUDENT
    HomeScreen(User(
        username = "mals",
        name = "Malavika",
        birthDate = Date(),
        role = Role.STUDENT
    ))
}


@Composable
fun DashboardScreen(username: String, role: Role) {
    val screens = remember(role) {
        Screen.values().filter { role in it.roles }
    }
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()

    Scaffold(
        scaffoldState = scaffoldState,
        modifier = Modifier.fillMaxSize(),
        backgroundColor = MaterialTheme.colors.background,
        topBar = {
            TopAppBar(
                title = { Text(text = "EduTrack") },
                navigationIcon = {
                    IconButton(onClick = {
                        coroutineScope.launch {
                            scaffoldState.drawerState.open()
                        }
                    }) {
                        Icon(imageVector  = Icons.Outlined.Menu, "Menu")
                    }
                }
            )
        },
        drawerContent = {
            NavDrawer(name = username, screens = screens, navigate = {
                if (it !in listOf(Screen.Home.route, Screen.TimeTableStudent.route)) {
                    return@NavDrawer
                }
                navController.navigate(it)
            })
        }
    ) {
       Column(modifier = Modifier.padding(it)) {
           NavHost(navController = navController, startDestination = Screen.Home.route) {
               composable(Screen.Home.route) {
                   HomeScreen(user = User(
                       username,
                       "PersonABC",
                       Date(),
                       Role.STUDENT
                   ))
               }
               composable(Screen.TimeTableStudent.route) {
                   TimeTableScreen()
               }
           }
       }
    }
}


@Composable
fun NavDrawer(name: String, screens: List<Screen>, navigate: (String) -> Unit) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        content = {
            item {
                Text(text = "Hi, $name!", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            }
            items(screens) {
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navigate(it.route) },
                verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = it.icon, contentDescription = "Navigate to ${it.title}", modifier = Modifier.padding(8.dp))
                    Text(text = it.title)
                }
            }
        }
    )
}