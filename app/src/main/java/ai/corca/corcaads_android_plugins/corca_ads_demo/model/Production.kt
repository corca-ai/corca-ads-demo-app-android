package ai.corca.corcaads_android_plugins.corca_ads_demo.model

data class Production(
    val productId: String,
    val name: String,
    val image: String,
    val price: String = "",
    val logOption: DemoLogOption,
    val isSuggested: Boolean,
)
