package com.example.mobiletreasurehunt.ui.screens.clue1Screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.example.mobiletreasurehunt.R
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
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                ) {
                    Text(text = clue.description)
                }
            }

            Spacer(modifier = Modifier.height(dimensionResource(R.dimen.padding_medium)))

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
}
