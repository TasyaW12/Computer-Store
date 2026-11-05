/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */



package com.example.cupcake.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.example.cupcake.data.OrderUiState

class OrderViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OrderUiState())
    val uiState: StateFlow<OrderUiState> = _uiState.asStateFlow()

    private fun updateTotalPrice(state: OrderUiState): OrderUiState {
        return state.copy(
            totalPrice = state.processorPrice +
                    state.memoryPrice +
                    state.storagePrice +
                    state.monitorPrice +
                    state.graphicsCardPrice
        )
    }

    fun setDeviceType(device: String) {
        _uiState.update { currentState ->
            currentState.copy(deviceType = device)
        }
    }

    fun setProcessor(name: String, price: Int) {
        _uiState.update { currentState ->
            updateTotalPrice(
                currentState.copy(
                    processor = name,
                    processorPrice = price
                )
            )
        }
    }

    fun setMemory(name: String, price: Int) {
        _uiState.update { currentState ->
            updateTotalPrice(
                currentState.copy(
                    memory = name,
                    memoryPrice = price
                )
            )
        }
    }

    fun setStorage(name: String, price: Int) {
        _uiState.update { currentState ->
            updateTotalPrice(
                currentState.copy(
                    storage = name,
                    storagePrice = price
                )
            )
        }
    }
    fun setMonitor(name: String, price: Int) {
        _uiState.update { currentState ->
            updateTotalPrice(
                currentState.copy(
                    monitor = name,
                    monitorPrice = price
                )
            )
        }
    }

    fun setGraphicsCard(name: String, price: Int) {
        _uiState.update { currentState ->
            updateTotalPrice(
                currentState.copy(
                    graphicsCard = name,
                    graphicsCardPrice = price
                )
            )
        }
    }


    fun resetOrder() {
        _uiState.value = OrderUiState()
    }

}
