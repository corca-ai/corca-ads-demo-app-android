package ai.corca.corcaads_android_plugins.corca_ads_demo

import ai.corca.corcaads_android_plugins.corca_ads_demo.utils.MockProductListAdapter
import ai.corca.adcio_android_plugins.databinding.ActivityPlacementBinding
import ai.corca.corcaads_analytics.feature.CorcaAdsAnalytics
import ai.corca.corcaads_analytics.model.CorcaAdsLogOption
import ai.corca.corcaads_android_plugins.corca_ads_demo.helper.MockProductsProvider
import ai.corca.corcaads_android_plugins.corca_ads_demo.helper.OnClickThread
import ai.corca.corcaads_android_plugins.corca_ads_demo.helper.OnImpressionThread
import ai.corca.corcaads_android_plugins.corca_ads_demo.helper.productions
import ai.corca.corcaads_android_plugins.corca_ads_demo.user.Gender
import ai.corca.corcaads_android_plugins.corca_ads_demo.user.User
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.Calendar
import java.util.UUID
import kotlin.math.log

lateinit var currentUser: User
lateinit var currentLocation: String

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

        val appVersion = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            this.packageManager.getPackageInfo(this.packageName, PackageManager.PackageInfoFlags.of(0))
        } else {
            @Suppress("DEPRECATION")
            this.packageManager.getPackageInfo(this.packageName, 0)
        }.versionName

        val mockProductsProvider = MockProductsProvider()
        corcaAdsAnalytics = CorcaAdsAnalytics("7bbb703e-a30b-4a4a-91b4-c0a7d2303415", appVersion = appVersion, this)

        mockProductListAdapter = MockProductListAdapter(
            onImpressionItem = { logOption ->
                OnImpressionThread(
                    logOption = CorcaAdsLogOption(
                        adsetId = logOption.adsetId,
                        requestId = logOption.requestId
                    )
                ).start()
            },

            onClickItem = { logOption ->
                OnClickThread(
                    logOption = CorcaAdsLogOption(
                        adsetId = logOption.adsetId,
                        requestId = logOption.requestId
                    )
                ).start()
            },
        )

        val sampleBirthDate = Calendar.getInstance()
        sampleBirthDate.set(Calendar.YEAR, 2000)
        sampleBirthDate.set(Calendar.MONTH, Calendar.JANUARY)
        sampleBirthDate.set(Calendar.DAY_OF_MONTH, 31)

        currentUser = User(
            id = UUID.randomUUID().toString(),
            name = "User",
            birthDate = LocalDate.of(2000, 1, 31),
            gender = Gender.female,
        )

        currentLocation = "Seoul, Korea"

        mockProductsProvider.start()
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
