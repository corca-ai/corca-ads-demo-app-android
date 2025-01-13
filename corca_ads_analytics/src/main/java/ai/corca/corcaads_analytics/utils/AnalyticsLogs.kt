package ai.corca.corcaads_analytics.utils

import ai.corca.corcaads_analytics.exception.PlatformException

internal const val TRACE_EXCEPTION_TAG = "Corca_Ads_AnalyticsException"

internal fun PlatformException.toNetworkErrorLog(): String =
    "errorCode: $code\nerrorMessage: $errorMessage"
