
package com.example.cupcake

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.cupcake.data.DataSource
import com.example.cupcake.ui.OrderSummaryScreen
import com.example.cupcake.ui.OrderViewModel
import com.example.cupcake.ui.SelectOptionScreen
import com.example.cupcake.ui.StartOrderScreen

enum class CupcakeScreen(val title: String) {
    Start("Computer Store"),
    Processor("Choose Processor"),
    Memory("Choose Memory"),
    Storage("Choose Storage"),
    Monitor("Choose Monitor"),
    Graphics("Choose Graphics Card"),
    Summary("Order Summary")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CupcakeAppBar(
    currentScreen: CupcakeScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = {
            Text(currentScreen.title)
        },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        }
    )
}

@Composable
fun CupcakeApp(
    viewModel: OrderViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    val currentScreen = CupcakeScreen.valueOf(
        backStackEntry?.destination?.route
            ?: CupcakeScreen.Start.name
    )

    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            CupcakeAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }
    ) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = CupcakeScreen.Start.name,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {

            composable(route = CupcakeScreen.Start.name) {
                StartOrderScreen(
                    onNextButtonClicked = {
                        navController.navigate(CupcakeScreen.Processor.name)
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
            composable(route = CupcakeScreen.Processor.name) {
                SelectOptionScreen(
                    options = DataSource.processorOptions,
                    onSelectionChanged = { name, price ->
                        viewModel.setProcessor(name, price)
                    },
                    onNextButtonClicked = {
                        navController.navigate(CupcakeScreen.Memory.name)
                    },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    modifier = Modifier.fillMaxHeight()
                )
            }

            composable(route = CupcakeScreen.Memory.name) {
                SelectOptionScreen(
                    options = DataSource.memoryOptions,
                    onSelectionChanged = { name, price ->
                        viewModel.setMemory(name, price)
                    },
                    onNextButtonClicked = {
                        navController.navigate(CupcakeScreen.Storage.name)
                    },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    modifier = Modifier.fillMaxHeight()
                )
            }

            composable(route = CupcakeScreen.Storage.name) {
                SelectOptionScreen(
                    options = DataSource.storageOptions,
                    onSelectionChanged = { name, price ->
                        viewModel.setStorage(name, price)
                    },
                    onNextButtonClicked = {
                        navController.navigate(CupcakeScreen.Monitor.name)
                    },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    modifier = Modifier.fillMaxHeight()
                )
            }

            composable(route = CupcakeScreen.Monitor.name) {
                SelectOptionScreen(
                    options = DataSource.monitorOptions,
                    onSelectionChanged = { name, price ->
                        viewModel.setMonitor(name, price)
                    },
                    onNextButtonClicked = {
                        navController.navigate(CupcakeScreen.Graphics.name)
                    },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    modifier = Modifier.fillMaxHeight()
                )
            }

            composable(route = CupcakeScreen.Graphics.name) {
                SelectOptionScreen(
                    options = DataSource.graphicsCardOptions,
                    onSelectionChanged = { name, price ->
                        viewModel.setGraphicsCard(name, price)
                    },
                    onNextButtonClicked = {
                        navController.navigate(CupcakeScreen.Summary.name)
                    },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    modifier = Modifier.fillMaxHeight()
                )
            }

            composable(CupcakeScreen.Summary.name) {
                OrderSummaryScreen(
                    orderUiState = uiState,
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    onSendButtonClicked = { _, _ ->
                        // Order summary is ready to be shared.
                    },
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }
    }
}

private fun cancelOrderAndNavigateToStart(
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    viewModel.resetOrder()

    navController.popBackStack(
        CupcakeScreen.Start.name,
        inclusive = false
    )
}
