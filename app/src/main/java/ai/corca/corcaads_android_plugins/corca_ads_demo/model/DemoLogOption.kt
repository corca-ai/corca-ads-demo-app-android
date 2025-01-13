package ai.corca.corcaads_android_plugins.corca_ads_demo.model

data class DemoLogOption(
    // suggestion identifier
    val requestId: String,
    // advertisement identifier
    val adsetId: String,
) {

    override fun toString(): String {
        return "AnalyticsRequest(requestId: $requestId, adsetId: $adsetId)"
    }
}
