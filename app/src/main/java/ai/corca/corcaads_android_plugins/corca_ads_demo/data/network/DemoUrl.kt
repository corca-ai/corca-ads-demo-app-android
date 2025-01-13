package ai.corca.corcaads_android_plugins.corca_ads_demo.data.network

internal object DemoUrl {

    internal var baseUrl = "https://receiver.corca.dev/"

    private const val EVENTS = "v1/events"
    object EndPoint {
        const val impression = "$EVENTS/impression"
        const val click = "$EVENTS/click"
    }
}
