package la.me.leo.tabs.discovery.adapter.view_holder

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import la.me.leo.core.base.misc.BlurHashDecoder
import la.me.leo.core.base.misc.RoundRectOutlineProvider
import la.me.leo.core.base.ui.BaseViewHolder
import la.me.leo.core.base.utils.doOnPreDraw
import la.me.leo.core.base.utils.dp
import la.me.leo.core.base.utils.showTextOrSetGone
import la.me.leo.data.discovery.Item.Medium
import la.me.leo.tabs.R
import la.me.leo.tabs.databinding.ItemMediumBinding

internal class MediumViewHolder(
    parent: ViewGroup
) : BaseViewHolder<Medium, ItemMediumBinding>(
    ItemMediumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) {


    init {
        with(binding) {
            root.outlineProvider = RoundRectOutlineProvider(radius = context.dp(8).toFloat())
            root.clipToOutline = true
            tvRightText2.paintFlags = tvRightText2.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            tvRightText2.doOnPreDraw { tvRightText2.translationY = tvRightText1.height * 0.9f }
        }
    }

    override fun render(item: Medium) = with(binding) {
        Glide.with(itemView.context)
            .load(item.image)
            .apply(
                RequestOptions().placeholder(
                    BlurHashDecoder.createPlaceholderRatio17777(context, item.blurHash)
                )
            )
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(ivImage)
        tvLeftText1.showTextOrSetGone(item.leftText1)
        tvLeftText2.showTextOrSetGone(item.leftText2)
        vGradient.isVisible = tvLeftText1.isVisible || tvLeftText2.isVisible
        tvRightText1.text = item.rightText1
        tvRightText2.text = item.rightText2
        llRightTextsContainer.setBackgroundResource(
            if (item.showRightBadge) R.drawable.bg_special_price_badge else 0
        )
    }

}
