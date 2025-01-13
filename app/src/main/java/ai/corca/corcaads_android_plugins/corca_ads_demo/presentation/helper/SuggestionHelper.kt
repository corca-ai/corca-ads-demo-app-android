package ai.corca.corcaads_android_plugins.corca_ads_demo.presentation.helper

import ai.corca.corcaads_android_plugins.corca_ads_demo.presentation.utils.Production
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

private val _productState = MutableStateFlow(listOf(Production()))
val productions: StateFlow<List<Production>> = _productState

internal class MockProductsProvider(
    val context: Context
): Thread() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun run() {
        val jsonString = getProductionsJson()

        val gson = Gson()
        val listType = object : TypeToken<List<Production>>() {}.type
        val allProductions: List<Production> = gson.fromJson(jsonString, listType)

        _productState.value = allProductions
    }

    private fun getProductionsJson(): String {
        return try {
            context.assets.open("productions.json").bufferedReader().use { it.readText() }
        } catch (e: Exception) {
            e.printStackTrace()
            "[]"
        }
    }
}