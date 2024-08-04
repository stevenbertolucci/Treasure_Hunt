// Author: Steven Bertolucci
// Course: CS 492 - Mobile Application Development
// Institution: Oregon State University

package com.example.mobiletreasurehunt.ui.screens.clue1

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.mobiletreasurehunt.R
import com.example.mobiletreasurehunt.data.DataSource
import com.example.mobiletreasurehunt.haversine.Haversine
import com.example.mobiletreasurehunt.model.Clues
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ClueOneScreen(
    clue: Clues.ClueNumberOne,
    modifier: Modifier = Modifier,
    onCancelButtonClicked: () -> Unit = {},
    onNextButtonClicked: (Clues.ClueNumberOne) -> Unit = {},
    onSelectionChanged: (Clues.ClueNumberOne) -> Unit,
    context: Context,
) {
    var selectedItem by rememberSaveable { mutableStateOf("") }
    var showHintDialog by rememberSaveable { mutableStateOf(false) }
    var locationPermissionGranted by rememberSaveable { mutableStateOf(false) }
    val lessIntenseRed = Color(0xFFFF5555)
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val snackbarHostState = remember { SnackbarHostState() }

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        locationPermissionGranted = isGranted
    }

    LaunchedEffect(Unit) {
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            locationPermissionGranted = true
        } else {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    @SuppressLint("MissingPermission")
    fun getCurrentLocation(onLocationReceived: (Location?) -> Unit) {
        if (locationPermissionGranted) {
            val locationTask: Task<Location> = fusedLocationClient.lastLocation
            locationTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                onLocationReceived(task.result)
            } else {
                onLocationReceived(null)
            }
        }
    } else {
        onLocationReceived(null)
    }
}

    fun isLocationMatch(userLocation: Location?): Boolean {
        val targetLocation = Haversine(37.422, -122.084)

        userLocation?.let {
            val userHaversineLocation = Haversine(it.latitude, it.longitude)
            val distance = userHaversineLocation.haversine(targetLocation)
            return distance < 0.05 // 50 meters tolerance
        }
        return false
    }

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
            val coroutineScope = rememberCoroutineScope()
            Button(
                onClick = {
                    getCurrentLocation { userLocation ->
                        if (isLocationMatch(userLocation)) {
                            onNextButtonClicked(clue)
                        } else {

                            // Show a message that the location does not match
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Location does not match. Please try again.")
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.found_it))
            }
        }

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
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
        onSelectionChanged = {},
        context = LocalContext.current
    )
}
