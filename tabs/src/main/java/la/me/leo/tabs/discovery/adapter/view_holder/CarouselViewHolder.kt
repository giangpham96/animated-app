package la.me.leo.tabs.discovery.adapter.view_holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import la.me.leo.core.base.ui.BaseViewHolder
import la.me.leo.core.base.utils.showTextOrSetGone
import la.me.leo.data.discovery.Section
import la.me.leo.tabs.databinding.ItemCarouselBinding
import la.me.leo.tabs.discovery.adapter.CarouselAdapter
import la.me.leo.tabs.discovery.adapter.helper.CarouselSnapHelper

internal class CarouselViewHolder(
    parent: ViewGroup,
    private val onVenueClickListener: () -> Unit
) : BaseViewHolder<Section<*>, ItemCarouselBinding>(
    ItemCarouselBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) {

    private lateinit var adapter: CarouselAdapter

    val rvItems: RecyclerView get() = binding.rvItems

    init {
        setupRecyclerView()
    }

    override fun render(item: Section<*>) {
        binding.tvTitle.showTextOrSetGone(item.header)
        adapter.items = item.items
        adapter.notifyDataSetChanged()
    }

    private fun setupRecyclerView() = with(binding) {
        val layoutManager = LinearLayoutManager(itemView.context, RecyclerView.HORIZONTAL, false)
        rvItems.layoutManager = layoutManager
        rvItems.itemAnimator = null
        adapter = CarouselAdapter(onVenueClickListener)
        rvItems.adapter = adapter
        CarouselSnapHelper(
            context
        ).attachToRecyclerView(rvItems)
    }

}
