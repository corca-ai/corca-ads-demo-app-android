package ai.corca.corcaads_android_plugins.corca_ads_demo.model.exception

data class PlatformException(
    val code: Int,
    val errorMessage: String,
) : RuntimeException()