package la.me.leo.animatedapp.tabs.discovery.adapter.view_holder

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import la.me.leo.animatedapp.R
import la.me.leo.animatedapp.databinding.ItemHeroBinding
import la.me.leo.animatedapp.tabs.discovery.adapter.helper.HeroParallaxDelegate
import la.me.leo.animatedapp.tabs.discovery.adapter.helper.HeroRollingDelegate
import la.me.leo.animatedapp.tabs.discovery.adapter.helper.HeroVideoDelegate
import la.me.leo.core.base.misc.BlurHashDecoder
import la.me.leo.core.base.misc.RoundRectOutlineProvider
import la.me.leo.core.base.ui.BaseViewHolder
import la.me.leo.core.base.utils.doOnPreDraw
import la.me.leo.core.base.utils.dp
import la.me.leo.core.base.utils.showTextOrSetGone
import la.me.leo.data.discovery.Item.Hero

internal class HeroViewHolder(
    parent: ViewGroup,
    lifecycle: Lifecycle
) : BaseViewHolder<Hero, ItemHeroBinding>(
    ItemHeroBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) {

    private val parallaxDelegate: HeroParallaxDelegate
    private val videoDelegate: HeroVideoDelegate
    private val rollingDelegate: HeroRollingDelegate

    init {
        with(binding) {
            root.outlineProvider = RoundRectOutlineProvider(radius = context.dp(8).toFloat())
            root.clipToOutline = true
            tvRightText2.paintFlags = tvRightText2.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            tvRightText2.doOnPreDraw { tvRightText2.translationY = tvRightText1.height * 0.9f }
            parallaxDelegate =
                HeroParallaxDelegate(
                    holder = this@HeroViewHolder,
                    tvLeftText1 = tvLeftText1,
                    tvLeftText2 = tvLeftText2,
                    tvLeftText3 = tvLeftText3,
                    llRightTextsContainer = llRightTextsContainer,
                    vFade = vFade,
                    recyclerView = parent as RecyclerView
                )
            videoDelegate =
                HeroVideoDelegate(
                    holder = this@HeroViewHolder,
                    playerWidget = playerWidget,
                    recyclerView = parent,
                    lifecycle = lifecycle
                )
            rollingDelegate =
                HeroRollingDelegate(
                    holder = this@HeroViewHolder,
                    playerWidget = playerWidget,
                    recyclerView = parent,
                    lifecycle = lifecycle
                ) {
                    parent.smoothScrollToPosition(adapterPosition + 1)
                }
        }
    }

    override fun render(item: Hero) = with(binding) {
        Glide.with(itemView.context)
            .load(item.image)
            .apply(
                RequestOptions().placeholder(
                    BlurHashDecoder.createPlaceholderRatio17777(context, item.blurHash)
                )
            )
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(ivImage)
        tvLeftText1.text = item.leftText1
        tvLeftText2.showTextOrSetGone(item.leftText2)
        tvLeftText3.showTextOrSetGone(item.leftText3)
        vGradient.isVisible = tvLeftText2.isVisible || tvLeftText3.isVisible
        tvRightText1.text = item.rightText1
        tvRightText2.text = item.rightText2
        llRightTextsContainer.setBackgroundResource(
            if (item.showRightBadge) R.drawable.bg_special_price_badge else 0
        )
        videoDelegate.render()
        rollingDelegate.render()
    }

}

