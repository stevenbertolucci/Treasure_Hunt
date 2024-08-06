// Author: Steven Bertolucci
// Course: CS 492 - Mobile Application Development
// Institution: Oregon State University

package com.example.mobiletreasurehunt

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mobiletreasurehunt.data.DataSource
import com.example.mobiletreasurehunt.data.DataSource.clue
import com.example.mobiletreasurehunt.model.TreasureHuntViewModel
import com.example.mobiletreasurehunt.ui.screens.clue1.ClueOneInfoScreen
import com.example.mobiletreasurehunt.ui.screens.clue1.ClueOneScreen
import com.example.mobiletreasurehunt.ui.screens.clue2.ClueTwoInfoScreen
import com.example.mobiletreasurehunt.ui.screens.clue2.ClueTwoScreen
import com.example.mobiletreasurehunt.ui.screens.clue3.ClueThreeInfoScreen
import com.example.mobiletreasurehunt.ui.screens.clue3.ClueThreeScreen
import com.example.mobiletreasurehunt.ui.screens.clue4.ClueFourScreen
import com.example.mobiletreasurehunt.ui.screens.congratulation.CongratulationScreen
import com.example.mobiletreasurehunt.ui.screens.start.StartScreen
import com.example.mobiletreasurehunt.ui.screens.requestPermission.RequestPermissionScreen
import com.example.mobiletreasurehunt.ui.screens.rules.RulesScreen
import com.example.mobiletreasurehunt.ui.theme.CustomBlue

enum class TreasureHuntScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Permission(title = R.string.permission),
    Rules(title = R.string.rules),
    Clue1Screen(title = R.string.clue_1),
    Clue1InfoScreen(title = R.string.clue_info),
    Clue2Screen(title = R.string.clue_2),
    Clue2InfoScreen(title = R.string.clue_info),
    Clue3Screen(title =R.string.clue_3),
    Clue3InfoScreen(title = R.string.clue_info),
    Clue4Screen(title =R.string.clue_4),
    Clue4InfoScreen(title = R.string.clue_info),
    Clue5Screen(title = R.string.clue_1),
    Clue5InfoScreen(title = R.string.clue_info),
    Clue6Screen(title = R.string.clue_2),
    Clue6InfoScreen(title = R.string.clue_info),
    Clue7Screen(title =R.string.clue_3),
    Clue7InfoScreen(title = R.string.clue_info),
    Clue8Screen(title =R.string.clue_4),
    Clue8InfoScreen(title = R.string.clue_info),
    Clue9Screen(title = R.string.clue_2),
    Clue9InfoScreen(title = R.string.clue_info),
    Clue10Screen(title = R.string.clue_2),
    Congratulation(title = R.string.clue_info),
}

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TreasureHuntAppBar(
    @StringRes currentScreenTitle: Int,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = { Text(stringResource(currentScreenTitle)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = CustomBlue,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White,
            navigationIconContentColor = Color.White
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun TreasureHuntApp() {

    // Create ViewModel
    val viewModel: TreasureHuntViewModel = viewModel()
    val navController: NavHostController = rememberNavController()

    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()

    // Get the name of the current screen
    val currentScreen = TreasureHuntScreen.valueOf(
        backStackEntry?.destination?.route ?: TreasureHuntScreen.Start.name
    )

    Scaffold(
        topBar = {

            TreasureHuntAppBar(
                currentScreenTitle = currentScreen.title,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = TreasureHuntScreen.Permission.name,
        ) {

            // Permission Screen Composable
            composable(route = TreasureHuntScreen.Permission.name) {

                RequestPermissionScreen(
                    onPermissionGranted = {
                        navController.navigate(TreasureHuntScreen.Start.name) {
                            popUpTo(TreasureHuntScreen.Permission.name) { inclusive = true }
                        }
                    },
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding)
                )
            }

            // Start Screen Composable
            composable(route = TreasureHuntScreen.Start.name) {
                StartScreen(
                    onStartButtonClicked = {
                        navController.navigate(TreasureHuntScreen.Clue1Screen.name)
                    },
                    onAnotherButtonClicked = {
                        navController.navigate(TreasureHuntScreen.Rules.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            // Rules Screen Composable
            composable(route = TreasureHuntScreen.Rules.name) {
                RulesScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                )
            }

            // Clue #1 Screen Composable
            composable(route = TreasureHuntScreen.Clue1Screen.name) {
                ClueOneScreen(
                    clue = DataSource.clue,
                    onCancelButtonClicked = {
                        navController.popBackStack(TreasureHuntScreen.Start.name, inclusive = false)
                    },
                    onNextButtonClicked = { clue ->
                        viewModel.updateClue(clue.description)
                        val category = viewModel.uiState.value.selectedClue
                        Log.d("ClueNavigation", "Selected Clue Category: $category")
                        val nextScreen = when (category) {
                            "\"I just went in there, and Hector is going to be running 3 Honda Civics with spoon engines. And on top of that, he just went into Harry's, and he ordered 3 T66 turbos, with NOS... and a Motec system exhaust.\"" -> TreasureHuntScreen.Clue1InfoScreen.name
                            else -> TreasureHuntScreen.Clue1Screen.name
                        }
                        navController.navigate(nextScreen)
                    },
                    onSelectionChanged = { clue -> viewModel.updateClue(clue.description) },
                    context = LocalContext.current,
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding)
                )
            }

            // Clue #1 Info Screen Composable
            composable(route = TreasureHuntScreen.Clue1InfoScreen.name) {

                ClueOneInfoScreen(
                    onNextButtonClicked = {
                        viewModel.updateClue(clue.description)
                        val category = viewModel.uiState.value.selectedClue
                        Log.d("Category", "Category: $category")
                        val nextScreen = when (category) {
                            "\"I just went in there, and Hector is going to be running 3 Honda Civics with spoon engines. And on top of that, he just went into Harry's, and he ordered 3 T66 turbos, with NOS... and a Motec system exhaust.\"" -> TreasureHuntScreen.Clue2Screen.name
                            else -> TreasureHuntScreen.Clue1Screen.name
                        }
                        navController.navigate(nextScreen)
                    },
                    onCancelButtonClicked = {
                        navController.popBackStack(TreasureHuntScreen.Start.name, inclusive = false)
                    },
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding)
                )
            }

            // Clue #2 Screen Composable
            composable(route = TreasureHuntScreen.Clue2Screen.name) {

                ClueTwoScreen(
                    clue = DataSource.clueTwo,
                    onCancelButtonClicked = {
                        navController.popBackStack(TreasureHuntScreen.Start.name, inclusive = false)
                    },
                    onNextButtonClicked = {
                        val nextScreen = TreasureHuntScreen.Clue2InfoScreen.name
                        navController.navigate(nextScreen)
                    },
                    onSelectionChanged = { clue -> viewModel.updateClue(clue.description) },
                    context = LocalContext.current,
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding)
                )
            }

            // Clue #2 Info Screen Composable
            composable(route = TreasureHuntScreen.Clue2InfoScreen.name) {

                ClueTwoInfoScreen(
                    onNextButtonClicked = {
                        viewModel.updateClue(clue.description)
                        val category = viewModel.uiState.value.selectedClue
                        Log.d("Category", "Category: $category")
                        val nextScreen = when (category) {
                            "\"I just went in there, and Hector is going to be running 3 Honda Civics with spoon engines. And on top of that, he just went into Harry's, and he ordered 3 T66 turbos, with NOS... and a Motec system exhaust.\"" -> TreasureHuntScreen.Clue3Screen.name
                            else -> TreasureHuntScreen.Clue2Screen.name
                        }
                        navController.navigate(nextScreen)
                    },
                    onCancelButtonClicked = {
                        navController.popBackStack(TreasureHuntScreen.Start.name, inclusive = false)
                    },
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding)
                )
            }

            // Clue #3 Screen Composable
            composable(route = TreasureHuntScreen.Clue3Screen.name) {

                ClueThreeScreen(
                    clue = DataSource.clueThree,
                    onCancelButtonClicked = {
                        navController.popBackStack(TreasureHuntScreen.Start.name, inclusive = false)
                    },
                    onNextButtonClicked = {
                        val nextScreen = TreasureHuntScreen.Clue3InfoScreen.name
                        navController.navigate(nextScreen)
                    },
                    onSelectionChanged = { clue -> viewModel.updateClue(clue.description) },
                    context = LocalContext.current,
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding)
                )
            }

            // Clue #3 Info Screen Composable
            composable(route = TreasureHuntScreen.Clue3InfoScreen.name) {

                ClueThreeInfoScreen(
                    onNextButtonClicked = {
                        viewModel.updateClue(clue.description)
                        val category = viewModel.uiState.value.selectedClue
                        Log.d("Category", "Category: $category")
                        val nextScreen = when (category) {
                            "\"I just went in there, and Hector is going to be running 3 Honda Civics with spoon engines. And on top of that, he just went into Harry's, and he ordered 3 T66 turbos, with NOS... and a Motec system exhaust.\"" -> TreasureHuntScreen.Clue4Screen.name
                            else -> TreasureHuntScreen.Clue3Screen.name
                        }
                        navController.navigate(nextScreen)
                    },
                    onCancelButtonClicked = {
                        navController.popBackStack(TreasureHuntScreen.Start.name, inclusive = false)
                    },
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding)
                )
            }

            // Clue #4 Screen Composable
            composable(route = TreasureHuntScreen.Clue4Screen.name) {
                ClueFourScreen(
                    clue = DataSource.clueFour,
                    onCancelButtonClicked = {
                        navController.popBackStack(TreasureHuntScreen.Start.name, inclusive = false)
                    },
                    onNextButtonClicked = {
                        val nextScreen = TreasureHuntScreen.Congratulation.name
                        navController.navigate(nextScreen)
                    },
                    onSelectionChanged = { clue -> viewModel.updateClue(clue.description) },
                    context = LocalContext.current,
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding)
                )
            }

            // Congratulations Screen Composable
            composable(route = TreasureHuntScreen.Congratulation.name) {

                CongratulationScreen(
                    onCancelButtonClicked = {
                        navController.popBackStack(TreasureHuntScreen.Start.name, inclusive = false)
                    },
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TreasureHuntAppPreview() {
    TreasureHuntApp()
}