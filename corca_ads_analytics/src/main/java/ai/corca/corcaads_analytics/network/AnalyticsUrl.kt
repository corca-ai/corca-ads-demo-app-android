package ai.corca.corcaads_analytics.network

internal object AnalyticsUrl {

    internal var baseUrl = "https://receiver.corca.dev/"

    private const val EVENTS = "v1/events"
    object EndPoint {
        const val impression = "$EVENTS/impression"
        const val click = "$EVENTS/click"
        const val purchase = "$EVENTS/purchase"
        const val pageView = "$EVENTS/page-view/product-detail"
        const val addToCart = "$EVENTS/add-to-cart"
    }
}
