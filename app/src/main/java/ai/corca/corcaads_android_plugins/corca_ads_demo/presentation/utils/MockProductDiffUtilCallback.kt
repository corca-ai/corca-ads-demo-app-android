package ai.corca.corcaads_android_plugins.corca_ads_demo.presentation.utils

import androidx.recyclerview.widget.DiffUtil

object MockProductDiffUtilCallback : DiffUtil.ItemCallback<Production>() {

    override fun areItemsTheSame(oldItem: Production, newItem: Production): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Production, newItem: Production): Boolean {
        return oldItem.name == newItem.name
    }
}
