// Author: Steven Bertolucci
// Course: CS 492 - Mobile Application Development
// Institution: Oregon State University

package com.example.mobiletreasurehunt.ui.screens.clue2

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.mobiletreasurehunt.R
import com.example.mobiletreasurehunt.data.DataSource
import com.example.mobiletreasurehunt.haversine.Haversine
import com.example.mobiletreasurehunt.model.Clues
import com.example.mobiletreasurehunt.ui.stopwatch.Stopwatch
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.launch

@Composable
fun ClueTwoScreen(
    clue: Clues.ClueNumberTwo,
    modifier: Modifier = Modifier,
    onCancelButtonClicked: () -> Unit = {},
    onNextButtonClicked: (Clues.ClueNumberTwo) -> Unit = {},
    onSelectionChanged: (Clues.ClueNumberTwo) -> Unit,
    context: Context,
    isStopwatchRunning: Boolean,
    onStopwatchToggle: (Boolean) -> Unit
) {
    var showHintDialog by rememberSaveable { mutableStateOf(false) }
    var locationPermissionGranted by rememberSaveable { mutableStateOf(false) }
    val lessIntenseRed = Color(0xFFFF5555)
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    var showAlertDialog by rememberSaveable { mutableStateOf(false) }
    var alertDialogMessage by rememberSaveable { mutableStateOf("") }
    var isAlertDialogLoading by rememberSaveable { mutableStateOf(false) }
    var isStopwatchRunning by rememberSaveable { mutableStateOf(false) }

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

            val locationTask: Task<Location> = fusedLocationClient.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, null)

            locationTask.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val location = task.result
                    Log.d("Location", "User location: ${location?.latitude}, ${location?.longitude}")
                    onLocationReceived(location)
                } else {
                    onLocationReceived(null)
                }
            }
        } else {
            onLocationReceived(null)
        }
    }

    // Location of this clue
    fun isLocationMatch(userLocation: Location?): Boolean {

        val targetLocation = Haversine(33.927063, -118.343913)

        userLocation?.let {
            val userHaversineLocation = Haversine(it.latitude, it.longitude)
            val distance = userHaversineLocation.haversine(targetLocation)

            // Add logging to check the calculated distance
            Log.d("Location", "Distance to target: $distance km")

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

            val image: Painter = painterResource(id = R.drawable.winningswinning)
            Image(
                painter = image,
                contentDescription = stringResource(R.string.connor_quote),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(270.dp)
                    .padding(dimensionResource(R.dimen.padding_small))
            )

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Button(
                    onClick = { showHintDialog = true },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Yellow,
                        contentColor = Color.Black
                    ),
                    modifier = Modifier
                        .wrapContentWidth()
                        .border(
                            width = 3.dp,
                            color = Color.Black,
                            shape = RoundedCornerShape(22.dp)
                        )
                ) {
                    Text(text = stringResource(R.string.hint_clue_2))
                }
            }

            Spacer(
                modifier = Modifier
                    .height(80.dp),
            )

            // Stopwatch
            Stopwatch(
                isRunning = isStopwatchRunning,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 16.dp),
                onTimeUpdate = {

                }
            )

            Spacer(
                modifier = Modifier
                    .height(80.dp),
            )

            // Found It! button
            val coroutineScope = rememberCoroutineScope()
            Button(
                onClick = {
                    alertDialogMessage = "Verifying your location. Please wait..."
                    isAlertDialogLoading = true
                    showAlertDialog = true

                    getCurrentLocation { location ->
                        coroutineScope.launch {
                            if (location == null) {
                                alertDialogMessage = "Failed to retrieve location. Please ensure location services are enabled and try again."
                                isAlertDialogLoading = false
                                showAlertDialog = true
                            } else {
                                if (isLocationMatch(location)) {
                                    onStopwatchToggle(false)
                                    onNextButtonClicked(clue)
                                } else {
                                    alertDialogMessage = "Location does not match. Please try again."
                                    isAlertDialogLoading = false
                                    showAlertDialog = true
                                }
                            }
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 4.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(22.dp)
                    )
            ) {
                Text(text = stringResource(R.string.found_it))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Quit button
            Button(
                onClick = onCancelButtonClicked,
                colors = ButtonDefaults.buttonColors(containerColor = lessIntenseRed),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 4.dp,
                        color = Color.Black,
                        shape = RoundedCornerShape(22.dp)
                    )
            ) {
                Text(text = stringResource(R.string.quit))
            }
        }

        if (showAlertDialog) {
            AlertDialog(
                onDismissRequest = { showAlertDialog = false },
                confirmButton = {
                    if (isAlertDialogLoading) {
                        CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                    } else {
                        Button(onClick = { showAlertDialog = false }) {
                            Text(text = stringResource(R.string.ok))
                        }
                    }
                },
                text = { Text(text = alertDialogMessage) }
            )
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
                Text(text = stringResource(R.string.hint_description2))
            }
        )
    }

    // Start the stopwatch when the clue is revealed
    LaunchedEffect(clue) {
        isStopwatchRunning = true
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewClueTwoScreen() {
    ClueTwoScreen(
        clue = DataSource.clueTwo,
        onCancelButtonClicked = {},
        onNextButtonClicked = {},
        onSelectionChanged = {},
        context = LocalContext.current,
        isStopwatchRunning = false,
        onStopwatchToggle = {}
    )
}