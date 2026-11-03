
package com.example.cupcake.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.cupcake.data.OrderUiState
import com.example.cupcake.ui.theme.CupcakeTheme
import java.text.NumberFormat
import java.util.Locale

@Composable
fun OrderSummaryScreen(
    orderUiState: OrderUiState,
    onCancelButtonClicked: () -> Unit,
    onSendButtonClicked: (String, String) -> Unit,
    modifier: Modifier = Modifier
) {

    val currencyFormat = NumberFormat.getCurrencyInstance(
        Locale("id", "ID")
    )

    val items = listOf(
        Pair("Processor", orderUiState.processor),
        Pair("Memory", orderUiState.memory),
        Pair("Storage", orderUiState.storage),
        Pair("Monitor", orderUiState.monitor),
        Pair("Graphics Card", orderUiState.graphicsCard)
    )

    val totalPrice = currencyFormat.format(orderUiState.totalPrice)

    val newOrder = "Computer Store - Order Summary"

    val orderSummary = buildString {
        appendLine("Computer Store Order")
        appendLine()

        items.forEach { item ->
            appendLine("${item.first}: ${item.second.ifEmpty { "Belum dipilih" }}")
        }

        appendLine()
        appendLine("Total Harga: $totalPrice")
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween
    ) {

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Text(
                text = "Order Summary",
                fontWeight = FontWeight.Bold
            )

            Divider()

            items.forEach { item ->

                Text(
                    text = item.first.uppercase(),
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = item.second.ifEmpty {
                        "Belum dipilih"
                    }
                )

                Divider()
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "TOTAL HARGA",
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = totalPrice,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    onSendButtonClicked(newOrder, orderSummary)
                }
            ) {
                Text("Send Order")
            }

            OutlinedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = onCancelButtonClicked
            ) {
                Text("Cancel Order")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OrderSummaryPreview() {
    CupcakeTheme {
        OrderSummaryScreen(
            orderUiState = OrderUiState(
                processor = "Intel Core i5",
                memory = "16 GB",
                storage = "SSD 512 GB",
                monitor = "24 Inch",
                graphicsCard = "RTX 4060",
                processorPrice = 3000000,
                memoryPrice = 650000,
                storagePrice = 750000,
                monitorPrice = 1800000,
                graphicsCardPrice = 5000000,
                totalPrice = 11200000
            ),
            onSendButtonClicked = { _, _ -> },
            onCancelButtonClicked = {},
            modifier = Modifier.fillMaxHeight()
        )
    }
}
