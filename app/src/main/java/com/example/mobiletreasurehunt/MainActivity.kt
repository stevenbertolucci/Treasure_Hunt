// Author: Steven Bertolucci
// Course: CS 492 - Mobile Application Development
// Institution: Oregon State University

package com.example.mobiletreasurehunt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mobiletreasurehunt.ui.theme.MobileTreasureHuntTheme
import com.example.mobiletreasurehunt.ui.screens.requestPermission.RequestPermissionScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MobileTreasureHuntTheme {
                TreasureHuntApp()
            }
        }
    }
}
