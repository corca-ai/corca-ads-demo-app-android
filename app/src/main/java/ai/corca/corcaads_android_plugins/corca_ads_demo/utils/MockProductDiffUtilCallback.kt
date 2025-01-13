package ai.corca.corcaads_android_plugins.corca_ads_demo.utils

import ai.corca.corcaads_android_plugins.corca_ads_demo.model.Production
import androidx.recyclerview.widget.DiffUtil

object MockProductDiffUtilCallback : DiffUtil.ItemCallback<Production>() {

    override fun areItemsTheSame(oldItem: Production, newItem: Production): Boolean {
        return oldItem.productId == newItem.productId
    }

    override fun areContentsTheSame(oldItem: Production, newItem: Production): Boolean {
        return oldItem == newItem
    }
}
