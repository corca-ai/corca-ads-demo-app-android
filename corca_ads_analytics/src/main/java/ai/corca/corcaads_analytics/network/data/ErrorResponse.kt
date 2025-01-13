package ai.corca.corcaads_analytics.network.data

import ai.corca.corcaads_analytics.exception.PlatformException
import ai.corca.corcaads_analytics.exception.UNKNOWN_EXCEPTION_MESSAGE
import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("statusCode") val statusCode: Int,
    @SerializedName("message") val message: List<String>? = listOf(""),
)

fun ErrorResponse.toException(): PlatformException =
    PlatformException(
        code = statusCode,
        errorMessage = message?.get(0) ?: UNKNOWN_EXCEPTION_MESSAGE
    )
