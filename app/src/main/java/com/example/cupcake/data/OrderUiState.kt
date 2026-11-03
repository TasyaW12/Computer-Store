
package com.example.cupcake.data

data class OrderUiState(

    // Pilihan komponen komputer
    val processor: String = "",
    val memory: String = "",
    val storage: String = "",
    val monitor: String = "",
    val graphicsCard: String = "",

    // Harga masing-masing komponen
    val processorPrice: Int = 0,
    val memoryPrice: Int = 0,
    val storagePrice: Int = 0,
    val monitorPrice: Int = 0,
    val graphicsCardPrice: Int = 0,

    // Total harga komputer
    val totalPrice: Int = 0
)
