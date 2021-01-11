package la.me.leo.tabs.discovery.adapter

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import la.me.leo.core.base.ui.BaseViewHolder
import la.me.leo.data.discovery.Item.Hero
import la.me.leo.data.discovery.Section
import la.me.leo.tabs.discovery.adapter.view_holder.CarouselViewHolder
import la.me.leo.tabs.discovery.adapter.view_holder.HeroCarouselViewHolder

internal class FlexyAdapter(
    private val lifecycle: Lifecycle,
    private val onVenueClickListener: () -> Unit
) : RecyclerView.Adapter<BaseViewHolder<*, *>>() {

    var items = listOf<Section<*>>()
    private val savedNestedScrollPositions = mutableMapOf<String, Int>()

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position].items.first()) {
            is Hero -> TYPE_HERO_CAROUSEL
            else -> TYPE_CAROUSEL
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*, *> {
        return when (viewType) {
            TYPE_CAROUSEL -> CarouselViewHolder(
                parent, onVenueClickListener
            )
            TYPE_HERO_CAROUSEL -> HeroCarouselViewHolder(
                parent,
                lifecycle
            )
            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*, *>, position: Int) {
        holder.bind(items[position])
        restoreNestedScrollPosition(holder)
    }

    override fun onViewDetachedFromWindow(holder: BaseViewHolder<*, *>) {
        saveNestedScrollPosition(holder)
    }

    private fun restoreNestedScrollPosition(holder: BaseViewHolder<*, *>) {
        if (holder is CarouselViewHolder) {
            val layoutManager = holder.rvItems.layoutManager as LinearLayoutManager
            val position = savedNestedScrollPositions[holder.item.id] ?: 0
            layoutManager.scrollToPositionWithOffset(position, 0)
        } else if (holder is HeroCarouselViewHolder) {
            val layoutManager = holder.rvItems.layoutManager as LinearLayoutManager
            val defaultPosition = if (holder.adapter.infinite) holder.adapter.items.size else 0
            val position = savedNestedScrollPositions[holder.item.id] ?: defaultPosition
            layoutManager.scrollToPositionWithOffset(position, 0)
        }
    }

    private fun saveNestedScrollPosition(holder: BaseViewHolder<*, *>) {
        if (holder is CarouselViewHolder) {
            val layoutManager = holder.rvItems.layoutManager as LinearLayoutManager
            savedNestedScrollPositions[holder.item.id] =
                layoutManager.findFirstCompletelyVisibleItemPosition()
        } else if (holder is HeroCarouselViewHolder) {
            val layoutManager = holder.rvItems.layoutManager as LinearLayoutManager
            savedNestedScrollPositions[holder.item.id] =
                layoutManager.findFirstCompletelyVisibleItemPosition()
        }
    }

}

private const val TYPE_CAROUSEL = 100002
private const val TYPE_HERO_CAROUSEL = 100003
