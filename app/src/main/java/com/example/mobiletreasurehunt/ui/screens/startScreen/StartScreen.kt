package com.example.mobiletreasurehunt.ui.screens.startScreen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobiletreasurehunt.R

@Composable
fun StartScreen(
    onStartButtonClicked: () -> Unit,
    modifier: Modifier = Modifier
) {
    TODO()
}

@Preview
@Composable
fun StartScreenPreview(){
    StartScreen(
        onStartButtonClicked = {},
        modifier = Modifier
            .padding(dimensionResource(R.dimen.padding_medium))
            .fillMaxSize()
    )
}