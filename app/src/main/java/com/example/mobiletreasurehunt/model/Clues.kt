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
}