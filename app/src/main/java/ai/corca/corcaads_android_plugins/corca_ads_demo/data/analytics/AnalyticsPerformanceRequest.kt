package ai.corca.corcaads_android_plugins.corca_ads_demo.data.analytics

import com.google.gson.annotations.SerializedName

data class AnalyticsPerformanceRequest(
    @SerializedName("sessionId") val sessionId: String,
    @SerializedName("deviceId") val deviceId: String,
    @SerializedName("customerId") val customerId: String?,
    @SerializedName("storeId") val storeId: String,
    @SerializedName("requestId") val requestId: String,
    @SerializedName("adsetId") val adsetId: String,
    @SerializedName("userAgent") val userAgent: String,
    @SerializedName("appVersion") val appVersion: String,
) {
    override fun toString(): String {
        return "AnalyticsRequest(requestId: $requestId, adsetId: $adsetId)"
    }
}
