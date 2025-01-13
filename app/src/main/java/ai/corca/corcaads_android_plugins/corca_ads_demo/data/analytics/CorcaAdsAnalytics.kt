package ai.corca.corcaads_android_plugins.corca_ads_demo.data.analytics

import ai.corca.corcaads_android_plugins.corca_ads_demo.data.network.RetrofitClient
import ai.corca.corcaads_android_plugins.corca_ads_demo.model.entity.toException
import ai.corca.corcaads_android_plugins.corca_ads_demo.model.exception.PlatformException
import android.os.Build
import android.util.Log
import retrofit2.Response

class CorcaAdsAnalytics {

    private val networkSuccessRange = 200 until 300

    fun onImpression(
        sessionId: String,
        deviceId: String,
        customerId: String?,
        storeId: String,
        requestId: String,
        adsetId: String,
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
                requestId = requestId,
                adsetId = adsetId,
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
        requestId: String,
        adsetId: String,
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
                requestId = requestId,
                adsetId = adsetId,
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
                "TRACE_EXCEPTION_TAGr",
                platformException.errorMessage
            )
            platformException
        } else null
}
