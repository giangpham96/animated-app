package la.me.leo.core.base.ui

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<T : Any, B : ViewBinding>(
    protected val binding: B
) : RecyclerView.ViewHolder(binding.root) {

    protected val context: Context
        get() = binding.root.context

    lateinit var item: T

    @Suppress("UNCHECKED_CAST")
    fun bind(item: Any) {
        this.item = item as T
        render(item)
    }

    protected open fun render(item: T) {}

}
