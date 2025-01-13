package ai.corca.corcaads_analytics.model

data class CorcaAdsLogOption(
    // suggestion identifier
    val requestId: String,
    // advertisement identifier
    val adsetId: String,
) {

    override fun toString(): String {
        return "AnalyticsRequest(requestId: $requestId, adsetId: $adsetId)"
    }
}
