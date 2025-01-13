package ai.corca.corcaads_android_plugins.corca_ads_demo.utils

import ai.corca.corcaads_android_plugins.corca_ads_demo.model.DemoLogOption
import ai.corca.corcaads_android_plugins.corca_ads_demo.model.Production

fun getMockProducts(): List<Production> = listOf(
    Production(
        productId = "1",
        name = "Product 1",
        price = "10000",
        image = "https://picsum.photos/200/300",
        logOption = DemoLogOption(requestId = "2023081805292275799", adsetId = "1"),
        isSuggested = false
    ),
    Production(
        productId = "2",
        name = "Product 2",
        price = "12000",
        image = "https://picsum.photos/200/200",
        logOption = DemoLogOption(requestId = "2023081805292275799", adsetId = "2"),
        isSuggested = false
    ),
    Production(
        productId = "3",
        name = "Product 3",
        price = "13000",
        image = "https://picsum.photos/300/300",
        logOption = DemoLogOption(requestId = "2023081805292275799", adsetId = "3"),
        isSuggested = false
    ),
)
