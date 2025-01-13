package ai.corca.corcaads_analytics.network.service

import ai.corca.corcaads_analytics.network.AnalyticsUrl
import ai.corca.corcaads_analytics.network.data.AnalyticsPerformanceRequest
import ai.corca.corcaads_analytics.network.data.event.AnalyticsAddToCartRequest
import ai.corca.corcaads_analytics.network.data.event.AnalyticsPageViewRequest
import ai.corca.corcaads_analytics.network.data.event.AnalyticsPurchaseRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AnalyticsService {

    @POST(AnalyticsUrl.EndPoint.impression)
    fun onImpression(
        @Body analyticsPerformanceRequest: AnalyticsPerformanceRequest
    ): Call<Unit>

    @POST(AnalyticsUrl.EndPoint.click)
    fun onClick(
        @Body analyticsPerformanceRequest: AnalyticsPerformanceRequest
    ): Call<Unit>

    @POST(AnalyticsUrl.EndPoint.purchase)
    fun onPurchase(
        @Body analyticsPurchaseRequest: AnalyticsPurchaseRequest
    ): Call<Unit>

    @POST(AnalyticsUrl.EndPoint.pageView)
    fun onPageView(
        @Body analyticsPageViewRequest: AnalyticsPageViewRequest
    ): Call<Unit>

    @POST(AnalyticsUrl.EndPoint.addToCart)
    fun onAddToCart(
        @Body analyticsAddToCartRequest: AnalyticsAddToCartRequest
    ): Call<Unit>
}
