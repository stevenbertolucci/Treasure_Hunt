// Author: Steven Bertolucci
// Course: CS 492 - Mobile Application Development
// Institution: Oregon State University

package com.example.mobiletreasurehunt.ui.screens.clue3

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.mobiletreasurehunt.R
import com.example.mobiletreasurehunt.data.DataSource
import com.example.mobiletreasurehunt.data.DataSource.clue
import com.example.mobiletreasurehunt.ui.stopwatch.Stopwatch

@Composable
fun ClueThreeInfoScreen(
    modifier: Modifier = Modifier,
    onNextButtonClicked: () -> Unit = {},
    //isStopwatchRunning: Boolean,
    onCancelButtonClicked: () -> Unit = {},
) {
    val showDialog = remember { mutableStateOf(true) }

    // For alert dialog
    LaunchedEffect(Unit) {
        showDialog.value = true
    }

    if (showDialog.value) {

        AlertDialog(
            onDismissRequest = { showDialog.value = false },
            title = {
                Text(text = "Clue 3 of 10 Completed!")
            },
            text = {
                Text("You completed the third clue. Please read more information regarding Clue #3 before continuing on to the next clue!")
            },
            confirmButton = {
                Button(
                    onClick = { showDialog.value = false }
                ) {
                    Text("OK")
                }
            }
        )

    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = stringResource(R.string.clue_3_info),
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(1.dp))

        // Display the clue in a card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_small))
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_medium))
            ) {
                Text(text = stringResource(R.string.clue_3_details))
            }
        }

//        // Stopwatch
//        Stopwatch(
//            isRunning = isStopwatchRunning,
//            modifier = Modifier
//                .align(Alignment.CenterHorizontally)
//                .padding(top = 16.dp),
//            onTimeUpdate = {
//
//            }
//        )

        Spacer(modifier = Modifier.height(460.dp))

        // Next Clue button
        Button(
            onClick = onNextButtonClicked,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 4.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(22.dp)
                )
        ) {
            Text(text = stringResource(R.string.next_clue))
        }
    }
}