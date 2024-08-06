// Author: Steven Bertolucci
// Course: CS 492 - Mobile Application Development
// Institution: Oregon State University

package com.example.mobiletreasurehunt.ui.screens.rules

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mobiletreasurehunt.R // Update with your actual package name

@Composable
fun RulesScreen(
    modifier: Modifier = Modifier
) {
    val rules = listOf(
        "Strap in your favorite shoes because we are going on a hunt! A TREASURE HUNT!",
        "This game is very simple! There will be a sequence of clues that you need to solve.",
        "There is no time limit, but you will be recorded for the time it takes you to solve all the clues. The only time the stopwatch gets paused is when you solved the clue. We like to give you an opportunity to take a breather... a literal breather for taking the effort making this far and read some more information regarding the solved location. The stopwatch will resume when you click on \'Next Clue\' button. Make sure to give yourself plenty of rest before starting on the next clue. ",
        "Each clue will have either a textual clue, an image clue, or both and you will need to go to the location of that clue.",
        "You will have unlimited guesses as this game checks your location every time you think you are at the right location.",
        "GOOD LUCK, PLAYERS! Game On!"
    )

    LazyColumn(
        state = rememberLazyListState(),
        modifier = modifier.padding(16.dp) // Add some padding if needed
    ) {
        item {
            Text(
                text = "TREASURE HUNT RULES",
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold, // Set the title text to bold
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            )
        }

        itemsIndexed(rules) { index, rule ->
            Text(
                text = rule,
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            if (index < rules.size - 1) {
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    thickness = 1.dp,
                    color = Color.Gray
                )
            }
        }

        // Header for "About Me"
        item {
            Text(
                text = "ABOUT ME",
                style = TextStyle(
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 30.dp)
            )
        }

        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                val image: Painter = painterResource(id = R.drawable.tom)
                Image(
                    painter = image,
                    contentDescription = "A famous picture of Tom Anderson of MySpace",
                    modifier = Modifier.size(400.dp)
                )
                Text(
                    text = "Buy me a coffee. No, that's not really me. But really, buy me a coffee :)",
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}