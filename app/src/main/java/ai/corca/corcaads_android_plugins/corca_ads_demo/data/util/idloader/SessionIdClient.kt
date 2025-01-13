package ai.corca.corcaads_android_plugins.corca_ads_demo.data.util.idloader

import java.util.UUID

object SessionClient {
    private val identifier: String by lazy { UUID.randomUUID().toString() }

    fun loadSessionId(): String = identifier
}
