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

        fun startVisibilityCheck() {
            if (visibleItemsMap.containsKey(adapterPosition)) return

            val runnable = object : Runnable {
                override fun run() {
                    val position = adapterPosition
                    if (position == RecyclerView.NO_POSITION) {
                        stopVisibilityCheck()
                        return
                    }

                    // 기준1. 관찰 대상의 50% 이상이 UI에 보여야 합니다.
                    if (isViewVisibleForAtLeast50Percent(binding.root)) {
                        if (!startTimeMap.containsKey(position)) {
                            startTimeMap[position] = System.currentTimeMillis()
                        }

                        val currentTime = System.currentTimeMillis()
                        val startTime = startTimeMap[position] ?: currentTime

                        // 기준2. 1초 이상 노출된 상품만 기록됩니다.
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
                // 기준3. 이미 기록된 상품은 재기록하지 않습니다.
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
