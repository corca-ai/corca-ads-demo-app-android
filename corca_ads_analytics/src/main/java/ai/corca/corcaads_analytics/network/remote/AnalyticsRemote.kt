package ai.corca.corcaads_analytics.network.remote

import ai.corca.corcaads_analytics.exception.PlatformException
import ai.corca.corcaads_analytics.model.CorcaAdsLogOption
import ai.corca.corcaads_analytics.network.RetrofitClient
import ai.corca.corcaads_analytics.network.data.AnalyticsPerformanceRequest
import ai.corca.corcaads_analytics.network.data.event.AnalyticsAddToCartRequest
import ai.corca.corcaads_analytics.network.data.event.AnalyticsPageViewRequest
import ai.corca.corcaads_analytics.network.data.event.AnalyticsPurchaseRequest
import ai.corca.corcaads_analytics.network.data.toException
import ai.corca.corcaads_analytics.utils.TRACE_EXCEPTION_TAG
import ai.corca.corcaads_analytics.utils.toNetworkErrorLog
import android.os.Build
import android.util.Log
import retrofit2.Response

internal class AnalyticsRemote {

    private val networkSuccessRange = 200 until 300
    private val sdkVersion = "Android 1.4.5"

    fun onImpression(
        sessionId: String,
        deviceId: String,
        customerId: String?,
        storeId: String,
        corcaAdsLogOption: CorcaAdsLogOption,
        userAgent: String? = null,
        baseUrl: String?,
        appVersion: String,
    ) {
        val service = RetrofitClient.getAnalyticsService(baseUrl)
        val response = service.onImpression(
            analyticsPerformanceRequest = AnalyticsPerformanceRequest(
                sessionId = sessionId,
                deviceId = deviceId,
                customerId = customerId,
                storeId = storeId,
                requestId = corcaAdsLogOption.requestId,
                adsetId = corcaAdsLogOption.adsetId,
                sdkVersion = sdkVersion,
                userAgent = userAgent ?: "${Build.MODEL} - ${Build.VERSION.RELEASE}",
                appVersion = appVersion
            )
        ).execute()

        checkError(response)?.let { throw it }
    }

    fun onClick(
        sessionId: String,
        deviceId: String,
        customerId: String?,
        storeId: String,
        corcaAdsLogOption: CorcaAdsLogOption,
        userAgent: String? = null,
        baseUrl: String?,
        appVersion: String,
    ) {
        val service = RetrofitClient.getAnalyticsService(baseUrl)
        val response = service.onClick(
            analyticsPerformanceRequest = AnalyticsPerformanceRequest(
                sessionId = sessionId,
                deviceId = deviceId,
                customerId = customerId,
                storeId = storeId,
                requestId = corcaAdsLogOption.requestId,
                adsetId = corcaAdsLogOption.adsetId,
                sdkVersion = sdkVersion,
                userAgent = userAgent ?: "${Build.MODEL} - ${Build.VERSION.RELEASE}",
                appVersion = appVersion
            )
        ).execute()

        checkError(response)?.let { throw it }
    }

    private fun checkError(response: Response<Unit>): PlatformException? =
        if (response.code() !in networkSuccessRange) {
            val platformException = RetrofitClient.exceptionHandling(response).toException()
            Log.e(
                TRACE_EXCEPTION_TAG,
                platformException.toNetworkErrorLog()
            )
            platformException
        } else null
}
