package la.me.leo.tabs.discovery.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import la.me.leo.core.base.ui.BaseViewHolder
import la.me.leo.data.discovery.Item
import la.me.leo.data.discovery.Item.Medium
import la.me.leo.data.discovery.Item.Venue
import la.me.leo.data.discovery.Item.SquareTitleBottom
import la.me.leo.tabs.discovery.adapter.view_holder.MediumViewHolder
import la.me.leo.tabs.discovery.adapter.view_holder.SquareTitleBottomViewHolder
import la.me.leo.tabs.discovery.adapter.view_holder.VenueViewHolder

internal class CarouselAdapter(
    private val onVenueClickListener: () -> Unit
) : RecyclerView.Adapter<BaseViewHolder<*, *>>() {

    var items: List<Item> = emptyList()

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is Medium -> TYPE_MEDIUM
            is Venue -> TYPE_VENUE
            is SquareTitleBottom -> TYPE_SQUARE_TITLE_BOTTOM
            else -> throw IllegalStateException()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*, *> {
        return when (viewType) {
            TYPE_MEDIUM -> MediumViewHolder(
                parent
            )
            TYPE_VENUE -> VenueViewHolder(
                parent, onVenueClickListener
            )
            TYPE_SQUARE_TITLE_BOTTOM -> SquareTitleBottomViewHolder(
                parent
            )
            else -> throw IllegalStateException()
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*, *>, position: Int) {
        holder.bind(items[position])
    }

}

private const val TYPE_MEDIUM = 0
private const val TYPE_VENUE = 1
private const val TYPE_SQUARE_TITLE_BOTTOM = 2
