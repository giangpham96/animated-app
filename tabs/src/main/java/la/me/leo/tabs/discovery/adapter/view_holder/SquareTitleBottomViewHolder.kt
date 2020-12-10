package la.me.leo.tabs.discovery.adapter.view_holder

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import la.me.leo.core.base.misc.BlurHashDecoder
import la.me.leo.core.base.misc.RoundRectOutlineProvider
import la.me.leo.core.base.ui.BaseViewHolder
import la.me.leo.core.base.utils.dp
import la.me.leo.core.base.utils.showTextOrSetGone
import la.me.leo.data.discovery.Item.SquareTitleBottom
import la.me.leo.tabs.databinding.ItemSquareBottomTitleBinding

internal class SquareTitleBottomViewHolder(
    parent: ViewGroup
) : BaseViewHolder<SquareTitleBottom, ItemSquareBottomTitleBinding>(
    ItemSquareBottomTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) {

    init {
        binding.root.outlineProvider = RoundRectOutlineProvider(radius = context.dp(8).toFloat())
        binding.root.clipToOutline = true
    }

    override fun render(item: SquareTitleBottom) = with(binding) {
        Glide.with(itemView.context)
            .load(item.image)
            .apply(
                RequestOptions().placeholder(
                    BlurHashDecoder.createPlaceholderRatio1F(context, item.blurHash)
                )
            )
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(ivImage)
        tvTitle.text = item.title
        tvTitle.setLines(if (item.desc == null) 2 else 1)
        tvDesc.showTextOrSetGone(item.desc)
    }

}
