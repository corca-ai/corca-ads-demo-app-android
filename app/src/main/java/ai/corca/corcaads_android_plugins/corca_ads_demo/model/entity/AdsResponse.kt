package ai.corca.corcaads_android_plugins.corca_ads_demo.model.entity

data class AdsResponse(
    val suggestions: List<Suggestion>,
    val placement: Placement
)

data class Suggestion(
    val logOptions: LogOptions,
    val product: Product
)

data class LogOptions(
    val requestId: String,
    val adsetId: String
)

data class Product(
    val id: String
)

data class Placement(
    val id: String,
    val title: String,
    val displayCount: Int,
    val activated: Boolean
)
