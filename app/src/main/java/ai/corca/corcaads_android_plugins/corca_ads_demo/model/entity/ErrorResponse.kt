package ai.corca.corcaads_android_plugins.corca_ads_demo.model.entity

import ai.corca.corcaads_android_plugins.corca_ads_demo.model.exception.PlatformException
import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("statusCode") val statusCode: Int,
    @SerializedName("message") val message: List<String>? = listOf(""),
)

fun ErrorResponse.toException(): PlatformException =
    PlatformException(
        code = statusCode,
        errorMessage = message?.get(0) ?: "UNKNOWN ERROR"
    )
