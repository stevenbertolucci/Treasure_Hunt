// Author: Steven Bertolucci
// Course: CS 492 - Mobile Application Development
// Institution: Oregon State University

package com.example.mobiletreasurehunt.ui.screens.start

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobiletreasurehunt.R

@Composable
fun StartScreen(
    onStartButtonClicked: () -> Unit,
    onAnotherButtonClicked: () -> Unit,
    isStopwatchRunning: Boolean,
    onStopwatchToggle: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {

    Box(modifier = modifier.fillMaxSize()) {

        Column(
            modifier = modifier
                .padding(bottom = 56.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            ElevatedCard(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.elevatedCardElevation(10.dp),
                colors = CardDefaults.elevatedCardColors(containerColor = Color.White.copy(alpha = 1.0f))
            ) {

                Text(
                    text = buildAnnotatedString {
                        append("Welcome to Treasure Hunt! \n\n\nHere, you will find clues that will lead you to a location. Keep solving clues to reach the final destination!\n\n\nAuthor: ")
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("Steven Bertolucci")
                        }
                        append("\nCourse: ")
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("CS492 - Mobile Application Development")
                        }
                        append("\nInstitution: ")
                        withStyle(style = SpanStyle(color = Color.Gray)) {
                            append("Oregon State University")
                        }
                    },
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.labelSmall.copy(fontSize = 14.sp),
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    onStopwatchToggle(true)
                    onStartButtonClicked()
                },
                Modifier
                    .widthIn(min = 250.dp),
                ) {
                Text(stringResource(R.string.start))
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = onAnotherButtonClicked,
                Modifier
                    .widthIn(min = 250.dp),
            ) {
                Text("RULES")
            }
        }
    }
}

@Preview
@Composable
fun StartScreenPreview(){
    StartScreen(
        onStartButtonClicked = {},
        onAnotherButtonClicked = {},
        isStopwatchRunning = false,
        onStopwatchToggle = {},
        modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_medium))
            .fillMaxSize()
    )
}