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
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun StudentAssignmentsScreen() {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {

    }
}

@Composable
fun AssignmentsCard() {
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
                Text(text = "CSE1002", fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text(text = "Computer Architecture")
                Spacer(modifier = Modifier.weight(1f))
                Text(text = "Assignment Title")
                Text(text = "17/08/26")
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
    AssignmentsCard()
}