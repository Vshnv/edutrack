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
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.createGraph
import kotlinx.coroutines.launch
import org.saintgits.edutrack.model.Role
import org.saintgits.edutrack.model.User
import org.saintgits.edutrack.screens.dashboard.screens.CreateUserScreen
import org.saintgits.edutrack.screens.dashboard.screens.HomeScreen
import org.saintgits.edutrack.screens.dashboard.screens.StudentAssignmentsScreen
import org.saintgits.edutrack.screens.dashboard.screens.StudentTimeTableScreen
import org.saintgits.edutrack.ui.theme.EdutrackTheme
import org.saintgits.edutrack.viewmodel.HomeViewModel
import org.saintgits.edutrack.viewmodel.result.LoadableState
import java.util.*

class DashboardActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val homeViewModel = viewModel(modelClass = HomeViewModel::class.java)
            EdutrackTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    DashboardScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreen()
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
    
}


@Composable
fun DashboardScreen() {
    val homeViewModel = viewModel(modelClass = HomeViewModel::class.java)


    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val navController = rememberNavController()

    val userState = homeViewModel.user.observeAsState(initial = LoadableState.Loading())
    val screens = remember(userState.value) {
        when (val userValue = userState.value) {
            is LoadableState.Result -> Screen.values().filter { userValue.result.role in it.roles }
            else -> emptyList<Screen>()
        }
    }
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
            when (val userLoadableState = userState.value) {
                is LoadableState.Error -> {
                    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                        Text(text = "Failed to load.")
                        Text(text = userLoadableState.message)
                    }
                }
                is LoadableState.Loading -> {
                    Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                        CircularProgressIndicator()
                    }
                }
                is LoadableState.Result -> {
                    NavDrawer(name = userLoadableState.result.name, screens = screens, navigate = {
                        if (it !in listOf(Screen.Home.route, Screen.TimeTableStudent.route, Screen.AssignmentsStudent.route, Screen.CreateUser.route)) {
                            return@NavDrawer
                        }
                        coroutineScope.launch {
                            scaffoldState.drawerState.close()
                        }
                        navController.navigate(it)
                    })
                }
            }
        }
    ) {
       Column(modifier = Modifier.padding(it)) {
           NavHost(navController = navController, startDestination = Screen.Home.route) {
               composable(Screen.Home.route) {
                   when (val userLoadableState = userState.value) {
                       is LoadableState.Error -> {
                           Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                               Text(text = "Failed to load.")
                               Text(text = userLoadableState.message)
                           }
                       }
                       is LoadableState.Loading -> {
                           Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                               CircularProgressIndicator()
                           }
                       }
                       is LoadableState.Result -> {
                           HomeScreen(user = userLoadableState.result)
                       }
                   }
               }
               composable(Screen.TimeTableStudent.route) {
                   when (val userLoadableState = userState.value) {
                       is LoadableState.Error -> {
                           Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                               Text(text = "Failed to load.")
                               Text(text = userLoadableState.message)
                           }
                       }
                       is LoadableState.Loading -> {
                           Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                               CircularProgressIndicator()
                           }
                       }
                       is LoadableState.Result -> {
                           StudentTimeTableScreen(user = userLoadableState.result)
                       }
                   }
               }
               composable(Screen.AssignmentsStudent.route) {
                   when (val userLoadableState = userState.value) {
                       is LoadableState.Error -> {
                           Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                               Text(text = "Failed to load.")
                               Text(text = userLoadableState.message)
                           }
                       }
                       is LoadableState.Loading -> {
                           Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                               CircularProgressIndicator()
                           }
                       }
                       is LoadableState.Result -> {
                           StudentAssignmentsScreen(user = userLoadableState.result)
                       }
                   }
               }
               composable(Screen.CreateUser.route) {
                   CreateUserScreen()
               }
           }
       }
        // 1 2 3 4
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