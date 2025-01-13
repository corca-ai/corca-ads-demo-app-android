package ai.corca.corcaads_android_plugins.corca_ads_demo.helper

import ai.corca.corcaads_analytics.model.CorcaAdsLogOption
import ai.corca.corcaads_android_plugins.corca_ads_demo.corcaAdsAnalytics
import android.os.Build


// Background Thread for Impression Analytics
internal class OnImpressionThread(
    private val logOption: CorcaAdsLogOption
): Thread() {
    override fun run() {
        // Called when a product recommended as a suggestion has at least 50% exposure on the screen for at least 1 second.
        corcaAdsAnalytics.onImpression(
            option = logOption,
            customerId = null,
            userAgent = "${Build.MODEL} - ${Build.VERSION.RELEASE}" // Example OS Version
        )
    }
}

// Background Thread for Click Analytics.
internal class OnClickThread(
    val logOption: CorcaAdsLogOption
) : Thread() {
    override fun run() {
        // If the user clicks on a particular product, call this function.
        corcaAdsAnalytics.onClick(
            option = logOption,
            customerId = null,
            userAgent = "${Build.MODEL} - ${Build.VERSION.RELEASE}" // Example OS Version
        )
    }
}
