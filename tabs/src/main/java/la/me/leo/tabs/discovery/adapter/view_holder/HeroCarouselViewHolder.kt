package la.me.leo.tabs.discovery.adapter.view_holder

import android.graphics.Canvas
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import la.me.leo.core.base.ui.BaseViewHolder
import la.me.leo.core.base.utils.startMargin
import la.me.leo.data.discovery.Item.Hero
import la.me.leo.data.discovery.Section
import la.me.leo.tabs.databinding.ItemHeroCarouselBinding
import la.me.leo.tabs.discovery.adapter.HeroCarouselAdapter

internal class HeroCarouselViewHolder(
    parent: ViewGroup,
    lifecycle: Lifecycle
) : BaseViewHolder<Section<Hero>, ItemHeroCarouselBinding>(
    ItemHeroCarouselBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) {
    lateinit var adapter: HeroCarouselAdapter

    val rvItems: RecyclerView get() = binding.rvItems

    init {
        setupRecyclerView(lifecycle)
    }

    override fun render(item: Section<Hero>) = with(binding) {
        adapter.items = item.items
        adapter.notifyDataSetChanged()
        if (item.items.size > 1) {
            pageIndicatorView
            pageIndicatorView.setPageCount(item.items.size)
        } else {
            pageIndicatorView.isVisible = false
        }
    }

    private fun setupRecyclerView(lifecycle: Lifecycle) = with(binding) {
        val layoutManager = LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL, false)
        rvItems.layoutManager = layoutManager
        rvItems.itemAnimator = null
        adapter =
            HeroCarouselAdapter(
                lifecycle
            )
        rvItems.adapter = adapter
        PagerSnapHelper().attachToRecyclerView(rvItems)
        setupPageIndicator()
    }

    private fun setupPageIndicator() = with(binding) {
        val layoutManager = rvItems.layoutManager as LinearLayoutManager
        val decoration = object : RecyclerView.ItemDecoration() {
            override fun onDraw(c: Canvas, rv: RecyclerView, state: RecyclerView.State) {
                val firstVisiblePosition = layoutManager.findFirstVisibleItemPosition()
                val firstChild = rv.findViewHolderForAdapterPosition(firstVisiblePosition)?.itemView
                if (firstChild != null) {
                    val offset =
                        (rv.paddingStart + itemView.startMargin() - firstChild.x) / firstChild.width
                    pageIndicatorView.onPageScrolled(
                        firstVisiblePosition % adapter.items.size,
                        offset
                    )
                }
            }
        }
        rvItems.addItemDecoration(decoration)
    }

}
