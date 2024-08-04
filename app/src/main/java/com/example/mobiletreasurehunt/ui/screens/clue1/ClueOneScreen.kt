// Author: Steven Bertolucci
// Course: CS 492 - Mobile Application Development
// Institution: Oregon State University

package com.example.mobiletreasurehunt.ui.screens.clue1

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mobiletreasurehunt.R
import com.example.mobiletreasurehunt.data.DataSource
import com.example.mobiletreasurehunt.model.Clues

@Composable
fun ClueOneScreen(
    clue: Clues.ClueNumberOne,
    modifier: Modifier = Modifier,
    onCancelButtonClicked: () -> Unit = {},
    onNextButtonClicked: (Clues.ClueNumberOne) -> Unit = {},
    onSelectionChanged: (Clues.ClueNumberOne) -> Unit
) {
    var selectedItem by rememberSaveable { mutableStateOf("") }
    var showHintDialog by rememberSaveable { mutableStateOf(false) }
    val lessIntenseRed = Color(0xFFFF5555)

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(dimensionResource(R.dimen.padding_medium))
    ) {
        Column {
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
                    Text(text = clue.description)
                }
            }

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center // Center the button within the Box
            ) {
                Button(
                    onClick = { showHintDialog = true },
                    modifier = Modifier.wrapContentWidth()
                ) {
                    Text(text = stringResource(R.string.hint_clue_1))
                }
            }

            Spacer(
                modifier = Modifier
                    .height(400.dp),
            )

            // Quit button
            Button(
                onClick = onCancelButtonClicked,
                colors = ButtonDefaults.buttonColors(containerColor = lessIntenseRed),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.quit))
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

            // Found It! button
            Button(
                onClick = { onNextButtonClicked(clue) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.found_it))
            }
        }
    }

    if (showHintDialog) {
        AlertDialog(
            onDismissRequest = { showHintDialog = false },
            confirmButton = {
                Button(
                    onClick = { showHintDialog = false }
                ) {
                    Text(text = stringResource(R.string.ok))
                }
            },
            text = {
                Text(text = stringResource(R.string.hint_description)) // Display the hint text
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewClueOneScreen() {
    ClueOneScreen(
        clue = DataSource.clue,
        onCancelButtonClicked = {},
        onNextButtonClicked = {},
        onSelectionChanged = {}
    )
}
