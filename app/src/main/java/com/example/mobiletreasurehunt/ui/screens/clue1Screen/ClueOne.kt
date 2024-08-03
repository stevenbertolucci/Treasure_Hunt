package com.example.mobiletreasurehunt.ui.screens.clue1Screen

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobiletreasurehunt.R
import com.example.mobiletreasurehunt.data.DataSource
import com.example.mobiletreasurehunt.model.Clues

@Composable
fun ClueOne(
    options: List<Clues.ClueNumberOne>,
    onCancelButtonClicked: () -> Unit,
    onNextButtonClicked: (Clues) -> Unit,
    onSelectionChanged: (Clues.ClueNumberOne) -> Unit,
    modifier: Modifier = Modifier
) {
    ClueOneScreen(
        options = options,
        onCancelButtonClicked = onCancelButtonClicked,
        onNextButtonClicked = onNextButtonClicked,
        onSelectionChanged = onSelectionChanged as (Clues) -> Unit,
        modifier = modifier
    )
}
@Preview
@Composable
fun ClueOneScreenPreview(){
    ClueOneScreen(
        options = DataSource.listOfClues,
        onCancelButtonClicked = {},
        onNextButtonClicked = {},
        onSelectionChanged = {},
        modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_medium))
            .verticalScroll(rememberScrollState())
    )
}
