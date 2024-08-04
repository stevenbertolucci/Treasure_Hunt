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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.mobiletreasurehunt.data.DataSource
import com.example.mobiletreasurehunt.model.TreasureHuntViewModel
import com.example.mobiletreasurehunt.ui.screens.clue1Screen.ClueOneScreen
import com.example.mobiletreasurehunt.ui.screens.clue2Screen.ClueTwoScreen
import com.example.mobiletreasurehunt.ui.screens.congratulationScreen.CongratulationScreen
import com.example.mobiletreasurehunt.ui.screens.startScreen.StartScreen

enum class TreasureHuntScreen(@StringRes val title: Int) {
    Start(title = R.string.app_name),
    Clue1Screen(title = R.string.clue_1),
    Clue2Screen(title = R.string.clue_2),
    Congratulation(title = R.string.congratulation)
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
            containerColor = Color.Cyan.copy(alpha = 0.1f)
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
            startDestination = TreasureHuntScreen.Start.name,
        ) {

            // Start Screen Composable
            composable(route = TreasureHuntScreen.Start.name) {
                StartScreen(
                    onStartButtonClicked = {
                        navController.navigate(TreasureHuntScreen.Clue1Screen.name)
                    },
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
                        viewModel.reset()
                        navController.popBackStack(TreasureHuntScreen.Start.name, inclusive = false)
                    },
                    onNextButtonClicked = { clue ->
                        viewModel.updateClue(clue.description)
                        val category = viewModel.uiState.value.selectedClue
                        Log.d("ClueNavigation", "Selected Clue Category: $category")
                        val nextScreen = when (category) {
                            "One does not simply walk into Mordor." -> TreasureHuntScreen.Clue2Screen.name
                            else -> TreasureHuntScreen.Clue1Screen.name
                        }
                        navController.navigate(nextScreen)
                    },
                    onSelectionChanged = { clue -> viewModel.updateClue(clue.description)},
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
                        viewModel.reset()
                        navController.popBackStack(TreasureHuntScreen.Start.name, inclusive = false)
                    },
                    onNextButtonClicked = {
                        val nextScreen = TreasureHuntScreen.Congratulation.name
                        navController.navigate(nextScreen)
                    },
                    onSelectionChanged = { clue -> viewModel.updateClue(clue.description)},
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding)
                )
            }

            // Congratulations Screen Composable
            composable(route = TreasureHuntScreen.Congratulation.name) {

                CongratulationScreen(
                    onCancelButtonClicked = {
                        viewModel.reset()
                        navController.popBackStack(TreasureHuntScreen.Start.name, inclusive = false) },
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .padding(innerPadding)
                )
            }
//
//            // Coffee Shops Screen Composable
//            composable(route = SanDiegoSpotsScreen.CoffeeShops.name) {
//
//                ChooseCoffeeShopsScreen(
//                    options = DataSource.listOfCoffeeShops,
//                    onCancelButtonClicked = {
//                        viewModel.reset()
//                        navController.popBackStack(SanDiegoSpotsScreen.Category.name, inclusive = false) },
//                    onNextButtonClicked = { navController.navigate(SanDiegoSpotsScreen.CoffeeShopsInfo.name) },
//                    onSelectionChanged = { coffeeShop -> viewModel.updateSelectedCoffeeShop(coffeeShop as Recommendations.CoffeeShops)},
//                    modifier = Modifier
//                        .verticalScroll(rememberScrollState())
//                        .padding(innerPadding)
//                )
//            }
//
//            // Coffee Shops Info Screen Composable
//            composable(route = SanDiegoSpotsScreen.CoffeeShopsInfo.name) {
//
//                val selectedCoffeeShop by viewModel.selectedCoffeeShop.collectAsState()
//
//                ShowCoffeeShopsInfoScreen(
//                    coffeeShop = selectedCoffeeShop,
//                    onCancelButtonClicked = {
//                        viewModel.reset()
//                        navController.popBackStack(SanDiegoSpotsScreen.Category.name, inclusive = false) },
//                    modifier = Modifier
//                        .verticalScroll(rememberScrollState())
//                        .padding(innerPadding)
//                )
//            }
//
//            // Parks Screen Composable
//            composable(route = SanDiegoSpotsScreen.Parks.name) {
//
//                ChooseParksScreen(
//                    options = DataSource.listOfParks,
//                    onCancelButtonClicked = {
//                        viewModel.reset()
//                        navController.popBackStack(SanDiegoSpotsScreen.Category.name, inclusive = false)
//                    },
//                    onSelectionChanged = { park -> viewModel.updateSelectedPark(park as Recommendations.Parks)},
//                    onNextButtonClicked = { navController.navigate(SanDiegoSpotsScreen.ParksInfo.name) },
//                    modifier = Modifier
//                        .verticalScroll(rememberScrollState())
//                        .padding(innerPadding)
//                )
//            }
//
//            // Parks Info Screen Composable
//            composable(route = SanDiegoSpotsScreen.ParksInfo.name) {
//
//                val selectedPark by viewModel.selectedPark.collectAsState()
//
//                ShowParksInfoScreen(
//                    park = selectedPark,
//                    onCancelButtonClicked = {
//                        viewModel.reset()
//                        navController.popBackStack(SanDiegoSpotsScreen.Category.name, inclusive = false) },
//                    modifier = Modifier
//                        .verticalScroll(rememberScrollState())
//                        .padding(innerPadding)
//                )
//            }
//
//            // Sport Venues Screen Composable
//            composable(route = SanDiegoSpotsScreen.SportsVenue.name) {
//
//                ChooseVenuesScreen(
//                    options = DataSource.listOfSportVenues,
//                    onCancelButtonClicked = {
//                        viewModel.reset()
//                        navController.popBackStack(SanDiegoSpotsScreen.Category.name, inclusive = false)
//                    },
//                    onNextButtonClicked = { navController.navigate(SanDiegoSpotsScreen.SportsVenueInfo.name) },
//                    onSelectionChanged = { venue -> viewModel.updateSelectedVenue(venue as Recommendations.SportVenues)},
//                    modifier = Modifier
//                        .verticalScroll(rememberScrollState())
//                        .padding(innerPadding)
//                )
//            }
//
//            // Sport Venues Info Screen Composable
//            composable(route = SanDiegoSpotsScreen.SportsVenueInfo.name) {
//
//                val selectedVenue by viewModel.selectedVenue.collectAsState()
//
//                ShowVenuesInfoScreen(
//                    venue = selectedVenue,
//                    onCancelButtonClicked = {
//                        viewModel.reset()
//                        navController.popBackStack(SanDiegoSpotsScreen.Category.name, inclusive = false) },
//                    modifier = Modifier
//                        .verticalScroll(rememberScrollState())
//                        .padding(innerPadding)
//                )
//            }
//
//            // Beach Screen Composable
//            composable(route = SanDiegoSpotsScreen.Beaches.name) {
//
//                ChooseBeachesScreen(
//                    options = DataSource.listOfBeaches,
//                    onCancelButtonClicked = {
//                        viewModel.reset()
//                        navController.popBackStack(SanDiegoSpotsScreen.Category.name, inclusive = false)
//                    },
//                    onNextButtonClicked = { navController.navigate(SanDiegoSpotsScreen.BeachesInfo.name) },
//                    onSelectionChanged = { beach -> viewModel.updateSelectedBeach(beach as Recommendations.Beaches)},
//                    modifier = Modifier
//                        .verticalScroll(rememberScrollState())
//                        .padding(innerPadding)
//                )
//            }
//
//            // Beaches Info Screen Composable
//            composable(route = SanDiegoSpotsScreen.BeachesInfo.name) {
//
//                val selectedBeach by viewModel.selectedBeach.collectAsState()
//
//                ShowBeachesInfoScreen(
//                    beach = selectedBeach,
//                    onCancelButtonClicked = {
//                        viewModel.reset()
//                        navController.popBackStack(SanDiegoSpotsScreen.Category.name, inclusive = false) },
//                    modifier = Modifier
//                        .verticalScroll(rememberScrollState())
//                        .padding(innerPadding)
//                )
//            }
          }
      }
}

@Preview(showBackground = true)
@Composable
fun TreasureHuntAppPreview() {
    TreasureHuntApp()
}