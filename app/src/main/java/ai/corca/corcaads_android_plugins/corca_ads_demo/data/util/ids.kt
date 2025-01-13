package ai.corca.corcaads_android_plugins.corca_ads_demo.data.util

import ai.corca.corcaads_android_plugins.corca_ads_demo.data.util.idloader.DeviceIdManager
import ai.corca.corcaads_android_plugins.corca_ads_demo.data.util.idloader.SessionClient
import android.content.Context

fun getSessionId(): String =
    SessionClient.loadSessionId()

fun getDeviceId(context: Context): String = DeviceIdManager.loadDeviceId(context)
