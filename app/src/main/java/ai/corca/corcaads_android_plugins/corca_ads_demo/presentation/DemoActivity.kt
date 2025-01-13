package ai.corca.corcaads_android_plugins.corca_ads_demo.presentation

import ai.corca.corcaads_android_plugins.corca_ads_demo.presentation.utils.MockProductListAdapter
import ai.corca.adcio_android_plugins.databinding.ActivityPlacementBinding
import ai.corca.corcaads_android_plugins.corca_ads_demo.data.analytics.CorcaAdsAnalytics
import ai.corca.corcaads_android_plugins.corca_ads_demo.presentation.helper.MockProductsProvider
import ai.corca.corcaads_android_plugins.corca_ads_demo.presentation.helper.OnClickThread
import ai.corca.corcaads_android_plugins.corca_ads_demo.presentation.helper.OnImpressionThread
import ai.corca.corcaads_android_plugins.corca_ads_demo.presentation.helper.productions
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch

lateinit var mockProductListAdapter: MockProductListAdapter

lateinit var corcaAdsAnalytics: CorcaAdsAnalytics

class SuggestionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlacementBinding

    @SuppressLint("NotifyDataSetChanged")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlacementBinding.inflate(layoutInflater)
        setContentView(binding.root)

        MockProductsProvider(this).start()
        corcaAdsAnalytics = CorcaAdsAnalytics()

        mockProductListAdapter = MockProductListAdapter(
            onImpressionItem = { logOption ->
                OnImpressionThread(
                    storeId = "7bbb703e-a30b-4a4a-91b4-c0a7d2303415",
                    logOption = logOption,
                    context = this
                ).start()
            },

            onClickItem = { logOption ->
                OnClickThread(
                    storeId = "7bbb703e-a30b-4a4a-91b4-c0a7d2303415",
                    logOption = logOption,
                    context = this
                ).start()
            },
        )

        fetchDemoData()

        binding.rvSuggestions.adapter = mockProductListAdapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchDemoData() = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            productions.collect { products ->
                if (products.isNotEmpty()) {
                    val myProducts = products.shuffled()
                    mockProductListAdapter.submitList(myProducts)
                    mockProductListAdapter.notifyDataSetChanged()
                    mockProductListAdapter.attachToRecyclerView(binding.rvSuggestions)
                }
            }
        }
    }
}
