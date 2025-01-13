package ai.corca.corcaads_android_plugins.corca_ads_demo.presentation.utils

import com.google.gson.annotations.SerializedName

data class Production(
    val requestId: String = "",
    val adsetId: String = "",
    val id: String = "",
    val name: String = "",
    val price: Int = 0,
    val image: String = "",
    val summary: String = ""
)