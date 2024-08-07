package com.example.mobiletreasurehunt.ui.screens.clue10

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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.example.mobiletreasurehunt.R
import com.example.mobiletreasurehunt.data.DataSource.clueTen
import com.example.mobiletreasurehunt.haversine.Haversine
import com.example.mobiletreasurehunt.model.Clues
import com.example.mobiletreasurehunt.ui.stopwatch.Stopwatch
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.launch

@Composable
fun ClueTenScreen(
    clue: Clues.ClueNumberTen,
    modifier: Modifier = Modifier,
    onCancelButtonClicked: () -> Unit = {},
    onNextButtonClicked: (Clues.ClueNumberTen) -> Unit = {},
    onSelectionChanged: (Clues.ClueNumberTen) -> Unit,
    context: Context,
    isStopwatchRunning: Boolean,
    onStopwatchToggle: (Boolean) -> Unit
) {
    var showHintDialog by rememberSaveable { mutableStateOf(false) }
    var locationPermissionGranted by rememberSaveable { mutableStateOf(false) }
    val lessIntenseRed = Color(0xFFFF5555)
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    val snackbarHostState = remember { SnackbarHostState() }
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

        val targetLocation = Haversine(38.5733, -109.5498)

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
                    Text(text = clueTen.description)
                }
            }

            val image: Painter = painterResource(id = R.drawable.austin_powers)
            Image(
                painter = image,
                contentDescription = stringResource(R.string.dodgers_stadium),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(280.dp)
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
                    Text(text = stringResource(R.string.hint_clue_10))
                }
            }

            Spacer(
                modifier = Modifier
                    .height(40.dp),
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
                    .height(60.dp),
            )

            // Quit button
            Button(
                onClick = {
                    onCancelButtonClicked()
                },
                colors = ButtonDefaults.buttonColors(containerColor = lessIntenseRed),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.quit))
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Found It! button
            val coroutineScope = rememberCoroutineScope()
            Button(
                onClick = {
                    getCurrentLocation { userLocation ->
                        coroutineScope.launch {
                            if (userLocation == null) {
                                snackbarHostState.showSnackbar("Failed to retrieve location. Please ensure location services are enabled and try again.")
                            } else {
                                snackbarHostState.showSnackbar("User location: ${userLocation.latitude}, ${userLocation.longitude}")
                                if (isLocationMatch(userLocation)) {
                                    onStopwatchToggle(false)
                                    onNextButtonClicked(clue)
                                } else {
                                    snackbarHostState.showSnackbar("Location does not match. Please try again.")
                                }
                            }
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = stringResource(R.string.found_it))
            }
        }

        // Notification Bar to inform users
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.Center)
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
                Text(text = stringResource(R.string.clue_10_description))
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
fun PreviewClueTenScreen() {
    ClueTenScreen(
        clue = clueTen,
        onCancelButtonClicked = {},
        onNextButtonClicked = {},
        onSelectionChanged = {},
        context = LocalContext.current,
        isStopwatchRunning = false,
        onStopwatchToggle = {}
    )
}