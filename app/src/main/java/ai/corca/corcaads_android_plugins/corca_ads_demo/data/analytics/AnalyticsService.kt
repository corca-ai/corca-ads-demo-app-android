package ai.corca.corcaads_android_plugins.corca_ads_demo.data.analytics

import ai.corca.corcaads_android_plugins.corca_ads_demo.data.network.DemoUrl
import ai.corca.corcaads_android_plugins.corca_ads_demo.data.analytics.AnalyticsPerformanceRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AnalyticsService {

    @POST(DemoUrl.EndPoint.impression)
    fun onImpression(
        @Body analyticsPerformanceRequest: AnalyticsPerformanceRequest
    ): Call<Unit>

    @POST(DemoUrl.EndPoint.click)
    fun onClick(
        @Body analyticsPerformanceRequest: AnalyticsPerformanceRequest
    ): Call<Unit>
}
