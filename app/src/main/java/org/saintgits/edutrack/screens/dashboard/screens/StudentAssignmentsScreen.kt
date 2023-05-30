package org.saintgits.edutrack.screens.dashboard.screens

import androidx.compose.foundation.background
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.saintgits.edutrack.model.Assignment
import org.saintgits.edutrack.model.User
import org.saintgits.edutrack.viewmodel.StudentAssignmentsViewModel
import org.saintgits.edutrack.viewmodel.result.LoadableState
import java.text.SimpleDateFormat

val format = SimpleDateFormat("dd/MM/yyyy")
@Composable
fun StudentAssignmentsScreen(user: User) {
    val assignmentsViewModel = viewModel<StudentAssignmentsViewModel>(factory = StudentAssignmentsViewModel.Factory(user))
    val assignmentsState by assignmentsViewModel.availableAssignments.observeAsState(initial = LoadableState.Loading())
    when (val assignments = assignmentsState) {
        is LoadableState.Error -> {
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                Text(text = "Failed to load.")
                Text(text = assignments.message)
            }
        }
        is LoadableState.Loading -> {
            Column(Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                CircularProgressIndicator()
            }
        }
        is LoadableState.Result -> {
            LazyColumn(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                items(assignments.result) {
                    AssignmentsCard(it)
                }
            }
        }
    }
}

@Composable
fun AssignmentsCard(assignment: Assignment) {
    Card(
        Modifier
            .fillMaxWidth()
            .height(150.dp)
            .padding(10.dp)) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .background(Color.White), verticalAlignment = Alignment.CenterVertically) {
            Column {
                Text(text = assignment.course, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text(text = "")
                Spacer(modifier = Modifier.weight(1f))
                Text(text = assignment.title)
                Text(text = format.format(assignment.due))
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(horizontalAlignment = Alignment.End, verticalArrangement = Arrangement.Center) {
                Button(onClick = { /*TODO*/ }) {
                    Text(text = "Submit")
                }
            }
        }
    }
}

@Preview
@Composable
fun StudentAssignmentsScreenPreview() {
    //AssignmentsCard()
}