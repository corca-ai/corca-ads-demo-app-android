package ai.corca.corcaads_analytics.feature

import ai.corca.corcaads_analytics.model.CorcaAdsLogOption
import ai.corca.corcaads_analytics.network.remote.AnalyticsRemote
import android.content.Context
import com.corcaai.core.ids.DeviceIdManager
import com.corcaai.core.ids.SessionClient

class CorcaAdsAnalytics(
    private val clientId: String,
    private val appVersion: String,
    private val context: Context,
) {

    private val storeID: String by lazy {
        clientId.ifEmpty {
            throw IllegalArgumentException("clientId cannot be empty")
        }
    }

    private val _appVersion: String by lazy {
        appVersion.ifEmpty {
            throw IllegalArgumentException("appVersion cannot be empty")
        }
    }

    private val analyticsRemote: AnalyticsRemote = AnalyticsRemote()

    fun getSessionId(): String =
        SessionClient.loadSessionId()

    fun getDeviceId(): String = DeviceIdManager.loadDeviceId(context)

    /**
     * click event log
     * This event is called when a user clicks on a recommended product displayed on a suggestion placement.
     */
    fun onClick(
        option: CorcaAdsLogOption,
        baseUrl: String? = null,
        customerId: String? = null,
        storeId: String? = null,
        userAgent: String? = null,
    ) {
        analyticsRemote.onClick(
            sessionId = SessionClient.loadSessionId(),
            deviceId = getDeviceId(),
            customerId = customerId,
            storeId = storeId ?: storeID,
            corcaAdsLogOption = option,
            userAgent = userAgent,
            baseUrl = baseUrl,
            appVersion = _appVersion
        )
    }

    /**
     * impression event log
     * This event is called when a suggestion placement is displayed on the screen during the ad lifecycle (e.g., page lifecycle). This call occurs only once when the suggestion placement is revealed.
     */
    fun onImpression(
        customerId: String? = null,
        storeId: String? = null,
        option: CorcaAdsLogOption,
        baseUrl: String? = null,
        userAgent: String? = null,
    ) {
        analyticsRemote.onImpression(
            sessionId = SessionClient.loadSessionId(),
            deviceId = getDeviceId(),
            customerId = customerId,
            storeId = storeId ?: storeID,
            corcaAdsLogOption = option,
            userAgent = userAgent,
            baseUrl = baseUrl,
            appVersion = _appVersion
        )
    }
}
