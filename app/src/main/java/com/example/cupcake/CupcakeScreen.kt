
package com.example.cupcake

import android.content.Context
import android.content.Intent

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

import com.example.cupcake.data.DataSource
import com.example.cupcake.ui.DeviceSelectionScreen
import com.example.cupcake.ui.OrderSummaryScreen
import com.example.cupcake.ui.OrderViewModel
import com.example.cupcake.ui.SelectOptionScreen
import com.example.cupcake.ui.StartOrderScreen
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue


enum class CupcakeScreen(val title: String) {
    Start("Computer Store"),
    DeviceSelection("Choose Your Device"),
    Processor("Choose Processor"),
    Memory("Choose RAM"),
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

            // 1. START SCREEN
            composable(route = CupcakeScreen.Start.name) {
                StartOrderScreen(
                    onNextButtonClicked = {
                        navController.navigate(
                            CupcakeScreen.DeviceSelection.name
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }


            // 2. DEVICE SELECTION: COMPUTER OR LAPTOP
            composable(route = CupcakeScreen.DeviceSelection.name) {
                DeviceSelectionScreen(
                    onDeviceSelected = { device ->

                        viewModel.setDeviceType(device)

                        navController.navigate(
                            CupcakeScreen.Processor.name
                        )
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }


            // 3. PROCESSOR
            composable(route = CupcakeScreen.Processor.name) {
                SelectOptionScreen(
                    options = DataSource.processorOptions,

                    onSelectionChanged = { name, price ->
                        viewModel.setProcessor(name, price)
                    },

                    onNextButtonClicked = {
                        navController.navigate(
                            CupcakeScreen.Memory.name
                        )
                    },

                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(
                            viewModel,
                            navController
                        )
                    },

                    modifier = Modifier.fillMaxHeight()
                )
            }


            // 4. RAM / MEMORY
            composable(route = CupcakeScreen.Memory.name) {
                SelectOptionScreen(
                    options = DataSource.memoryOptions,

                    onSelectionChanged = { name, price ->
                        viewModel.setMemory(name, price)
                    },

                    onNextButtonClicked = {
                        navController.navigate(
                            CupcakeScreen.Storage.name
                        )
                    },

                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(
                            viewModel,
                            navController
                        )
                    },

                    modifier = Modifier.fillMaxHeight()
                )
            }


            // 5. STORAGE
            composable(route = CupcakeScreen.Storage.name) {
                SelectOptionScreen(
                    options = DataSource.storageOptions,

                    onSelectionChanged = { name, price ->
                        viewModel.setStorage(name, price)
                    },

                    onNextButtonClicked = {
                        navController.navigate(
                            CupcakeScreen.Monitor.name
                        )
                    },

                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(
                            viewModel,
                            navController
                        )
                    },

                    modifier = Modifier.fillMaxHeight()
                )
            }
            // 6. Monitor
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
                        cancelOrderAndNavigateToStart(
                            viewModel,
                            navController
                        )
                    },
                    modifier = Modifier.fillMaxHeight()
                )
            }


            // 7. GRAPHICS CARD
            composable(route = CupcakeScreen.Graphics.name) {
                SelectOptionScreen(
                    options = DataSource.graphicsCardOptions,

                    onSelectionChanged = { name, price ->
                        viewModel.setGraphicsCard(name, price)
                    },

                    onNextButtonClicked = {
                        navController.navigate(
                            CupcakeScreen.Summary.name
                        )
                    },

                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(
                            viewModel,
                            navController
                        )
                    },

                    modifier = Modifier.fillMaxHeight()
                )
            }


            // 8. ORDER SUMMARY
            composable(route = CupcakeScreen.Summary.name) {

                val context = LocalContext.current

                OrderSummaryScreen(
                    orderUiState = uiState,

                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(
                            viewModel,
                            navController
                        )
                    },

                    onSendButtonClicked = { subject, summary ->
                        shareOrder(
                            context = context,
                            subject = subject,
                            summary = summary
                        )
                    },

                    modifier = Modifier.fillMaxHeight()
                )
            }
        }
    }
}


// CANCEL ORDER
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


// SHARE ORDER
private fun shareOrder(
    context: Context,
    subject: String,
    summary: String
) {

    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"

        putExtra(Intent.EXTRA_SUBJECT, subject)
        putExtra(Intent.EXTRA_TEXT, summary)
    }

    context.startActivity(
        Intent.createChooser(
            intent,
            "Share Computer Store Order"
        )
    )
}
