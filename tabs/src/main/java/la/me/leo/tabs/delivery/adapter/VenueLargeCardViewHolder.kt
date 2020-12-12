package la.me.leo.tabs.delivery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
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
import la.me.leo.core.base.view.XsRatingIconWidget
import la.me.leo.data.delivery.Venue
import la.me.leo.tabs.R
import la.me.leo.tabs.databinding.ItemVenueLargeCardBinding

internal class VenueLargeCardViewHolder(
        parent: ViewGroup,
) : BaseViewHolder<Venue, ItemVenueLargeCardBinding>(
    ItemVenueLargeCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
) {

    private val ivImage: ImageView get() = binding.ivImage
    private val tvOverlay: TextView get() = binding.tvOverlay
    private val tvName: TextView get() = binding.tvName
    private val tvDescription: TextView get() = binding.tvDescription
    private val tvDetails: TextView get() = binding.tvDetails
    private val ivRatingIcon: XsRatingIconWidget get() = binding.ivRatingIcon
    private val tvRating: TextView get() = binding.tvRating
    private val tvRatingDivider: TextView get() = binding.tvRatingDivider
    private val llEstimateContainer: LinearLayout get() = binding.llEstimateContainer
    private val tvEstimatedTime: TextView get() = binding.tvEstimatedTime
    private val ivFavorite: ImageView get() = binding.ivFavorite

    init {
        itemView.outlineProvider = RoundRectOutlineProvider(radius = context.dp(8).toFloat())
        itemView.clipToOutline = true
    }

    override fun render(item: Venue) {
        tvName.text = item.name
        setupImage()
        tvDescription.text = item.desc.orEmpty()
        if (item.deliveryEstimate != null) {
            tvEstimatedTime.text = item.deliveryEstimate
            llEstimateContainer.isVisible = true
        } else {
            llEstimateContainer.isVisible = false
        }
        val iconId = if (item.favorite) R.drawable.ic_heart_fill else R.drawable.ic_heart
        ivFavorite.setImageResource(iconId)
        tvDetails.text = item.details
        tvOverlay.showTextOrSetGone(item.overlayText)
        setupRatingViews(item)
    }

    private fun setupRatingViews(venue: Venue) {
        if (venue.rating5 != null && venue.rating10 != null) {
            tvRatingDivider.isVisible = true
            ivRatingIcon.setIcon(venue.rating5!!, venue.rating10!!)
            tvRating.text = String.format("%.1f", venue.rating10)
            ivRatingIcon.isVisible = true
            tvRating.isVisible = true
        } else {
            tvRatingDivider.isVisible = false
            ivRatingIcon.isInvisible = true
            tvRating.isInvisible = true
        }
    }

    private fun setupImage() {
        Glide.with(itemView.context)
                .load(item.image)
                .apply(RequestOptions()
                    .placeholder(BlurHashDecoder.createPlaceholderRatio2F(context, item.blurHash)))
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(ivImage)
    }

}
