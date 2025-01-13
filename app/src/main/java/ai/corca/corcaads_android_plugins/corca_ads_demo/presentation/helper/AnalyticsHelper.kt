package ai.corca.corcaads_android_plugins.corca_ads_demo.presentation.helper

import ai.corca.corcaads_android_plugins.corca_ads_demo.data.util.getDeviceId
import ai.corca.corcaads_android_plugins.corca_ads_demo.data.util.getSessionId
import ai.corca.corcaads_android_plugins.corca_ads_demo.model.entity.LogOptions
import ai.corca.corcaads_android_plugins.corca_ads_demo.presentation.corcaAdsAnalytics
import android.content.Context
import android.os.Build


// Background Thread for Impression Analytics
internal class OnImpressionThread(
    private val storeId: String,
    private val logOption: LogOptions,
    private val context: Context
): Thread() {
    override fun run() {
        // Called when a product recommended as a suggestion has at least 50% exposure on the screen for at least 1 second.
        corcaAdsAnalytics.onImpression(
            customerId = null,
            userAgent = "${Build.MODEL} - ${Build.VERSION.RELEASE}",
            sessionId = getSessionId(),
            deviceId = getDeviceId(context),
            storeId = storeId,
            requestId = logOption.requestId,
            adsetId = logOption.adsetId,
            baseUrl = "https://receiver.corca.dev/",
            appVersion = "1.0.0" // Example OS Version
        )
    }
}

// Background Thread for Click Analytics.
internal class OnClickThread(
    private val storeId: String,
    private val logOption: LogOptions,
    private val context: Context
) : Thread() {
    override fun run() {
        // If the user clicks on a particular product, call this function.
        corcaAdsAnalytics.onClick(
            customerId = null,
            userAgent = "${Build.MODEL} - ${Build.VERSION.RELEASE}",
            sessionId = getSessionId(),
            deviceId = getDeviceId(context),
            storeId = storeId,
            requestId = logOption.requestId,
            adsetId = logOption.adsetId,
            baseUrl = "https://receiver.corca.dev/",
            appVersion = "1.0.0" // Example OS Version
        )
    }
}
