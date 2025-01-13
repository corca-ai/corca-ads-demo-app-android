package ai.corca.corcaads_android_plugins.corca_ads_demo.data.util.idloader

import android.content.Context
import java.util.UUID

object DeviceIdManager {
    private const val PREFS_FILE = "DeviceIdPrefs"
    private const val PREFS_DEVICE_ID = "device_id"
    private var deviceId: String? = null

    fun loadDeviceId(context: Context): String {
        if (deviceId == null) {
            val sharedPreferences = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE)
            deviceId = sharedPreferences.getString(PREFS_DEVICE_ID, null)

            if (deviceId == null) {
                deviceId = UUID.randomUUID().toString()
                sharedPreferences.edit().putString(PREFS_DEVICE_ID, deviceId).apply()
            }
        }

        return deviceId!!
    }
}
