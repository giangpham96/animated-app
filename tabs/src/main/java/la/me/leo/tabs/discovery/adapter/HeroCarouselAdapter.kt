package la.me.leo.tabs.discovery.adapter

import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import la.me.leo.data.discovery.Item.Hero
import la.me.leo.tabs.discovery.adapter.view_holder.HeroViewHolder

internal class HeroCarouselAdapter(
    private val lifecycle: Lifecycle
) : RecyclerView.Adapter<HeroViewHolder>() {

    var items: List<Hero> = emptyList()
    val infinite: Boolean get() = items.size > 1

    override fun getItemCount() = if (infinite) Int.MAX_VALUE else items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeroViewHolder {
        return HeroViewHolder(
            parent,
            lifecycle
        )
    }

    override fun onBindViewHolder(holder: HeroViewHolder, position: Int) {
        holder.bind(items[position % items.size])
    }

}
