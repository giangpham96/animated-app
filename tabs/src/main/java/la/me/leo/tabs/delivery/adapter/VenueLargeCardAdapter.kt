package la.me.leo.tabs.delivery.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import la.me.leo.data.delivery.Venue

internal class VenueLargeCardAdapter: RecyclerView.Adapter<VenueLargeCardViewHolder>() {

    var items = listOf<Venue>()

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VenueLargeCardViewHolder {
        return VenueLargeCardViewHolder(parent)
    }

    override fun onBindViewHolder(holder: VenueLargeCardViewHolder, position: Int) {
        holder.bind(items[position])
    }

}
