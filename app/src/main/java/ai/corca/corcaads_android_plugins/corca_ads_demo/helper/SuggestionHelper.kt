package ai.corca.corcaads_android_plugins.corca_ads_demo.helper

import ai.corca.corcaads_android_plugins.corca_ads_demo.model.Production
import ai.corca.corcaads_android_plugins.corca_ads_demo.utils.getMockProducts
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

private val _productState = MutableStateFlow(emptyList<Production>())
val productions: StateFlow<List<Production>> = _productState

internal class MockProductsProvider: Thread() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun run() {
        val products: MutableList<Production> = getMockProducts().toMutableList()
        _productState.value = products
    }
}