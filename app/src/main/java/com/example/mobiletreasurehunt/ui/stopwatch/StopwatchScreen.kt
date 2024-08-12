// Author: Steven Bertolucci
// Course: CS 492 - Mobile Application Development
// Institution: Oregon State University

package com.example.mobiletreasurehunt.ui.stopwatch

import android.annotation.SuppressLint
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@SuppressLint("DefaultLocale")
@Composable
fun Stopwatch(
    isRunning: Boolean,
    modifier: Modifier = Modifier,
    onTimeUpdate: (Long) -> Unit = {}
) {
    var startTime by remember { mutableStateOf(0L) }
    var elapsedTime by remember { mutableStateOf(0L) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(isRunning) {
        if (isRunning) {
            startTime = System.currentTimeMillis()
            coroutineScope.launch {
                while (isRunning) {
                    elapsedTime = System.currentTimeMillis() - startTime
                    onTimeUpdate(elapsedTime)
                    kotlinx.coroutines.delay(1000L) // Update every second
                }
            }
        } else {
            elapsedTime = 0L
        }
    }

    val seconds = (elapsedTime / 1000) % 60
    val minutes = (elapsedTime / (1000 * 60)) % 60
    val hours = (elapsedTime / (1000 * 60 * 60))

    Text(
        text = String.format("%02d:%02d:%02d", hours, minutes, seconds),
        modifier = modifier
    )
}