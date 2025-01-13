package ai.corca.corcaads_android_plugins.corca_ads_demo.presentation.utils

import ai.corca.adcio_android_plugins.databinding.ItemMockProductBinding
import ai.corca.corcaads_android_plugins.corca_ads_demo.model.entity.LogOptions
import android.graphics.Rect
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

private val impressionHistory: MutableSet<String> = mutableSetOf()

internal fun hasImpression(adsetId: String): Boolean = impressionHistory.contains(adsetId)

class MockProductListAdapter(
    val onImpressionItem: (logOption: LogOptions) -> Unit,
    val onClickItem: (logOption: LogOptions) -> Unit,
) : ListAdapter<Production, MockProductListAdapter.MockProductViewHolder>(
    MockProductDiffUtilCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MockProductViewHolder {
        return MockProductViewHolder(
            ItemMockProductBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            )
        )
    }

    override fun onBindViewHolder(holder: MockProductViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MockProductViewHolder(val binding: ItemMockProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val handler = Handler(Looper.getMainLooper())
        private val startTimeMap = mutableMapOf<Int, Long>()
        private val visibleItemsMap = mutableMapOf<Int, Runnable>()

        fun bind(item: Production) = with(binding) {
            setOtherViews(item)

            binding.root.post {
                startVisibilityCheck()
            }
        }

        // Check screen 50% of the time for more than 1 second
        fun startVisibilityCheck() {
            if (visibleItemsMap.containsKey(adapterPosition)) return

            val runnable = object : Runnable {
                override fun run() {
                    val position = adapterPosition
                    if (position == RecyclerView.NO_POSITION) {
                        stopVisibilityCheck()
                        return
                    }

                    if (isViewVisibleForAtLeast50Percent(binding.root)) {
                        if (!startTimeMap.containsKey(position)) {
                            startTimeMap[position] = System.currentTimeMillis()
                        }

                        val currentTime = System.currentTimeMillis()
                        val startTime = startTimeMap[position] ?: currentTime
                        if (currentTime - startTime >= 1000) {
                            impression(position)
                            stopVisibilityCheck()
                            return
                        }
                    } else {
                        startTimeMap.remove(position)
                    }

                    handler.postDelayed(this, 500)
                }
            }

            visibleItemsMap[adapterPosition] = runnable
            handler.post(runnable)
        }


        fun stopVisibilityCheck() {
            startTimeMap.remove(adapterPosition)
            visibleItemsMap[adapterPosition]?.let { handler.removeCallbacks(it) }
            visibleItemsMap.remove(adapterPosition)
        }

        private fun isViewVisibleForAtLeast50Percent(view: View): Boolean {
            val rect = Rect()
            val isVisible = view.getGlobalVisibleRect(rect)
            val visibleHeight = rect.height()
            val totalHeight = view.height

            return isVisible && visibleHeight >= totalHeight / 2
        }

        private fun impression(position: Int) {
            if (position in 0 until itemCount) {
                val item = getItem(position)
                // Impression should only run once after the screen is launched.
                // This logic prevents Impression from being called multiple times
                if (!hasImpression(item.adsetId)) {
                    onImpressionItem(
                        LogOptions(
                            adsetId = item.adsetId,
                            requestId = item.requestId
                        )
                    )
                    impressionHistory.add(item.adsetId)
                }
            }
        }

        private fun setOtherViews(item: Production) {
            Glide.with(binding.root)
                .load(item.image)
                .into(binding.ivAnalyticsImage)

            binding.tvName.text = item.name
            binding.tvPrice.text = "₩${item.price}"

            binding.cardAnalyticsSuggestion.setOnClickListener {
                onClickItem(
                    LogOptions(
                        requestId = item.requestId,
                        adsetId = item.adsetId
                    )
                )
            }
        }
    }

    fun attachToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                checkVisibleItems(recyclerView)
            }
        })
    }

    private fun checkVisibleItems(recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
        val lastVisiblePosition = layoutManager.findLastVisibleItemPosition()

        for (position in firstVisiblePosition..lastVisiblePosition) {
            val viewHolder =
                recyclerView.findViewHolderForAdapterPosition(position) as? MockProductViewHolder
            viewHolder?.startVisibilityCheck()
        }
    }
}
