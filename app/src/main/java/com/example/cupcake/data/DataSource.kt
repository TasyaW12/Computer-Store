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
package com.example.cupcake.data

import com.example.cupcake.R

object DataSource {

    // Processor Options
    val processorOptions = listOf(
        "Intel Core i3" to 1800000,
        "Intel Core i5" to 3000000,
        "Intel Core i7" to 4800000,
        "AMD Ryzen 5" to 2200000,
        "AMD Ryzen 7" to 3800000
    )

    // Memory Options
    val memoryOptions = listOf(
        "8 GB" to 350000,
        "16 GB" to 650000,
        "32 GB" to 1200000,
        "64 GB" to 2300000
    )

    // Storage Options
    val storageOptions = listOf(
        "SSD 256 GB" to 450000,
        "SSD 512 GB" to 750000,
        "SSD 1 TB" to 1300000,
        "HDD 1 TB" to 650000
    )

    // Monitor Options
    val monitorOptions = listOf(
        "19 Inch" to 900000,
        "22 Inch" to 1300000,
        "24 Inch" to 1800000,
        "27 Inch" to 2800000
    )

    // Graphics Card Options
    val graphicsCardOptions = listOf(
        "GTX 1650" to 2200000,
        "RTX 3050" to 3500000,
        "RTX 4060" to 5000000,
        "RTX 4070" to 9500000
    )

}
