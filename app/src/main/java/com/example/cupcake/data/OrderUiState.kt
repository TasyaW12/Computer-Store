package com.example.cupcake.data

data class OrderUiState(
    val deviceType: String = "",

    val processor: String = "",
    val memory: String = "",
    val storage: String = "",
    val monitor: String = "",
    val graphicsCard: String = "",

    val processorPrice: Int = 0,
    val memoryPrice: Int = 0,
    val storagePrice: Int = 0,
    val monitorPrice: Int = 0,
    val graphicsCardPrice: Int = 0,

    val totalPrice: Int = 0
)