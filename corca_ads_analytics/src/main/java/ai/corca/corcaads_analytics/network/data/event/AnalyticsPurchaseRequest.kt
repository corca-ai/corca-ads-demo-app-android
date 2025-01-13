package ai.corca.corcaads_analytics.network.data.event

import com.google.gson.annotations.SerializedName

data class AnalyticsPurchaseRequest(
    @SerializedName("sessionId") val sessionId: String,
    @SerializedName("deviceId") val deviceId: String,
    @SerializedName("customerId") val customerId: String?,
    @SerializedName("orderId") val orderId: String,
    @SerializedName("storeId") val storeId: String,
    @SerializedName("requestId") val requestId: String?,
    @SerializedName("adsetId") val adsetId: String?,
    @SerializedName("categoryIdOnStore") val categoryIdOnStore: String?,
    @SerializedName("productIdOnStore") val productIdOnStore: String?,
    @SerializedName("quantity") val quantity: Int?,
    @SerializedName("amount") val amount: Int,
    @SerializedName("sdkVersion") val sdkVersion: String,
    @SerializedName("userAgent") val userAgent: String,
    @SerializedName("appVersion") val appVersion: String,
)