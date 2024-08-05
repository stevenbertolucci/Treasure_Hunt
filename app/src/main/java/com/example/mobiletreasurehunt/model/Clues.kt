// Author: Steven Bertolucci
// Course: CS 492 - Mobile Application Development
// Institution: Oregon State University

package com.example.mobiletreasurehunt.model

sealed class Clues (
    open val description: String,
) {
    data class ClueNumberOne(
        override val description: String,
    ) : Clues(description)

    data class ClueNumberTwo(
        override val description: String,
    ) : Clues(description)

    data class ClueNumberThree(
        override val description: String,
    ) : Clues(description)
}