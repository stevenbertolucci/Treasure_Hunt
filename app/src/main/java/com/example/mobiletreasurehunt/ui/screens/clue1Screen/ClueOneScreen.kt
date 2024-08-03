package com.example.mobiletreasurehunt.ui.screens.clue1Screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import com.example.mobiletreasurehunt.R
import com.example.mobiletreasurehunt.model.Clues

@Composable
fun ClueOneScreen(
    options: List<Clues.ClueNumberOne>,
    modifier: Modifier = Modifier,
    onCancelButtonClicked: () -> Unit = {},
    onNextButtonClicked: (Clues.ClueNumberOne) -> Unit = {},
    onSelectionChanged: (Clues.ClueNumberOne) -> Unit
) {
    var selectedItem by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = modifier) {
        options.forEach { item ->
            val onClick = {
                selectedItem = item.description
                onSelectionChanged(item)
                onNextButtonClicked(item)
            }
            MenuItemRow(
                item = item,
                selectedItem = selectedItem,
                onClick = onClick,
                modifier = Modifier.clickable(onClick = onClick)
            )
        }

        CancelButtonGroup(
            onCancelButtonClicked = onCancelButtonClicked,
            modifier = Modifier
                .fillMaxWidth()
                .padding(dimensionResource(R.dimen.padding_medium))
        )
    }
}

@Composable
fun MenuItemRow(
    item: Clues.ClueNumberOne,
    selectedItem: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(dimensionResource(R.dimen.padding_medium))
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_small)),
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = item.description,
            style = MaterialTheme.typography.headlineSmall
        )
        HorizontalDivider(
            modifier = Modifier.padding(bottom = dimensionResource(R.dimen.padding_medium)),
            thickness = dimensionResource(R.dimen.thickness_divider)
        )
    }
}

@Composable
fun CancelButtonGroup(
    onCancelButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
    ){
        OutlinedButton(modifier = Modifier.weight(1f), onClick = onCancelButtonClicked) {
            Text(stringResource(R.string.cancel).uppercase())
        }
    }
}