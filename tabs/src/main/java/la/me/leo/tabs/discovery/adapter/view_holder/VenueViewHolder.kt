package la.me.leo.tabs.discovery.adapter.view_holder

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import la.me.leo.core.base.misc.BlurHashDecoder
import la.me.leo.core.base.misc.RoundRectOutlineProvider
import la.me.leo.core.base.ui.BaseViewHolder
import la.me.leo.core.base.utils.dp
import la.me.leo.core.base.utils.showTextOrSetGone
import la.me.leo.data.discovery.Item.Venue
import la.me.leo.tabs.databinding.ItemVenueBinding

internal class VenueViewHolder(
    parent: ViewGroup
) : BaseViewHolder<Venue, ItemVenueBinding>(
    ItemVenueBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) {

    init {
        binding.root.outlineProvider = RoundRectOutlineProvider(radius = context.dp(8).toFloat())
        binding.root.clipToOutline = true
    }

    override fun render(item: Venue) = with(binding) {
        tvName.text = item.name
        setupImage()
        tvTags.text = item.tags
        tvDetails.text = item.details
        tvOverlay.showTextOrSetGone(item.overlayText)
        if (item.rating5 != null && item.rating10 != null) {
            tvRatingDivider.isVisible
            ivRatingIcon.setIcon(item.rating5!!, item.rating10!!)
            tvRating.text = String.format("%.1f", item.rating10)
            ivRatingIcon.isVisible = true
            tvRating.isVisible = true
        } else {
            tvRatingDivider.isVisible = false
            ivRatingIcon.isInvisible = true
            tvRating.isInvisible = true
        }
    }

    private fun setupImage() = with(binding) {
        val ratio = ivImage.layoutParams.width / ivImage.layoutParams.height.toDouble()
        Glide.with(itemView.context)
            .load(item.image)
            .apply(
                RequestOptions()
                    .placeholder(BlurHashDecoder.createPlaceholder(context, item.blurHash, ratio))
            )
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(ivImage)
    }

}
