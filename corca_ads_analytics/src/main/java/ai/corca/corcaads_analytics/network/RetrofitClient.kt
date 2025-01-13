package ai.corca.corcaads_analytics.network

import ai.corca.corcaads_analytics.exception.UNKNOWN_EXCEPTION_MESSAGE
import ai.corca.corcaads_analytics.network.data.ErrorResponse
import ai.corca.corcaads_analytics.network.service.AnalyticsService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.RuntimeException

internal object RetrofitClient {
    private var analyticsService: AnalyticsService? = null
    private lateinit var retrofit: Retrofit

    private val okHttpClient = OkHttpClient().newBuilder()
        .addInterceptor(
            interceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()

    fun getAnalyticsService(baseUrl: String?): AnalyticsService {

        if (analyticsService == null) {
            if (baseUrl.isNullOrBlank().not()) AnalyticsUrl.baseUrl = baseUrl ?: ""

            createRetrofit()
            analyticsService = retrofit.create(AnalyticsService::class.java)
            return analyticsService ?: throw RuntimeException()
        } else {
            return if (baseUrl.isNullOrBlank()) {
                analyticsService ?: throw RuntimeException()
            } else {
                if (baseUrl == AnalyticsUrl.baseUrl) {
                    analyticsService ?: throw RuntimeException()
                } else {
                    AnalyticsUrl.baseUrl = baseUrl
                    createRetrofit()
                    analyticsService = retrofit.create(AnalyticsService::class.java)
                    analyticsService ?: throw RuntimeException()
                }
            }
        }
    }

    fun exceptionHandling(response: Response<Unit>): ErrorResponse {
        val errorResponse = response.errorBody()?.let {
            retrofit.responseBodyConverter<ErrorResponse>(
                ErrorResponse::class.java,
                ErrorResponse::class.java.annotations
            ).convert(it)
        }?.message ?: listOf(UNKNOWN_EXCEPTION_MESSAGE)
        return ErrorResponse(
            statusCode = response.code(),
            message = errorResponse
        )
    }

    private fun createRetrofit() {
        retrofit = Retrofit.Builder().apply {
            baseUrl(AnalyticsUrl.baseUrl)
            client(okHttpClient)
            addConverterFactory(GsonConverterFactory.create())
        }.build()
    }
}
