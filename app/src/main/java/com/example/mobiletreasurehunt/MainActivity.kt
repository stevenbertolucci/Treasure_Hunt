// Author: Steven Bertolucci
// Course: CS 492 - Mobile Application Development
// Institution: Oregon State University

package com.example.mobiletreasurehunt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.mobiletreasurehunt.ui.theme.MobileTreasureHuntTheme

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
