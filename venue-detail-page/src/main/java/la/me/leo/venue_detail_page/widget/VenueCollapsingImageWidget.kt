package la.me.leo.venue_detail_page.widget

import android.animation.ArgbEvaluator
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import androidx.core.view.updateMarginsRelative
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import la.me.leo.core.base.misc.BlurHashDecoder
import la.me.leo.core.base.utils.getDimen
import la.me.leo.core.base.utils.statusBarHeight
import la.me.leo.core.base.utils.toolbarHeight
import la.me.leo.core_animation.CollapsingWidget
import la.me.leo.venue_detail_page.R
import la.me.leo.venue_detail_page.databinding.WidgetVenueCollapsingImageBinding

internal class VenueCollapsingImageWidget(context: Context, attrs: AttributeSet) :
    CollapsingWidget(context, attrs) {

    private val binding =
        WidgetVenueCollapsingImageBinding.inflate(LayoutInflater.from(context), this)

    private val argbEvaluator = ArgbEvaluator()
    private val expandedIconBgColor = ContextCompat.getColor(context, R.color.salt_72)
    private val collapsedIconBgColor = ContextCompat.getColor(context, R.color.pepper_8)

    var title: CharSequence? = null
        set(value) {
            field = value
            binding.tvTitle.text = value
        }

    var bigTitle: View? = null

    private var currentPhase: Phase = Phase.EXPANDED

    init {
        binding.toolbarSpace.layoutParams.height = context.toolbarHeight
        binding.toolbarSpace.updateLayoutParams<LayoutParams> {
            updateMargins(top = context.statusBarHeight)
        }
        onScrollPositionChanged(0f)
    }

    fun setImage(image: String, blurHash: String? = null) {
        val blurHashDrawable =
            blurHash?.let { BlurHashDecoder.createPlaceholderRatio3Div2(context, it) }
        Glide.with(context)
            .load(image)
            .apply(RequestOptions().placeholder(blurHashDrawable))
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.ivImage)
    }

    fun setLeftIcon(icon: Int?, clickListener: (() -> Unit)?) {
        binding.leftIconWidget.setIcon(icon, clickListener)
    }

    fun setRightIcon1(rightIcon: Int?, clickListener: (() -> Unit)?) {
        binding.rightIconWidget1.setIcon(rightIcon, clickListener)
    }

    fun setRightIcon2(rightIcon: Int?, clickListener: (() -> Unit)?) {
        binding.rightIconWidget2.setIcon(rightIcon, clickListener)
    }

    override fun onScrollPositionChanged(scrollY: Float) {
        val collapsingDistance = calcCollapsingDistance()
        determineCurrentPhase(scrollY, collapsingDistance)
        renderImage(scrollY, collapsingDistance)
        renderIconBackground(scrollY, collapsingDistance)
        renderMoreInfoIconSize(scrollY, collapsingDistance)
        renderSearchIconPosition(scrollY, collapsingDistance)
        renderTitles(scrollY, collapsingDistance)
        renderToolbarBackground(scrollY, collapsingDistance)
    }

    private fun determineCurrentPhase(scrollY: Float, collapsingDistance: Float) {
        val phase3ScrollRange = context.getDimen(R.dimen.u3)
        currentPhase = when {
            scrollY == 0f -> Phase.EXPANDED
            scrollY < collapsingDistance / 2 -> Phase.ONE
            scrollY < collapsingDistance -> Phase.TWO
            scrollY < collapsingDistance + phase3ScrollRange -> Phase.THREE
            scrollY < collapsingDistance + phase3ScrollRange + (bigTitle?.height ?: 0) -> Phase.FOUR
            else -> Phase.COLLAPSED
        }
    }

    private fun calcCollapsingDistance() = height.toFloat() - binding.toolbarSpace.bottom

    private fun calcPhase3and4ScrollRange(): Int {
        return context.getDimen(R.dimen.u3) + (bigTitle?.height ?: 0)
    }

    // Handle motion (1) and (2)
    private fun renderImage(scrollY: Float, collapsingDistance: Float) {
        val progress = when(currentPhase) {
            Phase.EXPANDED -> 0f
            Phase.ONE, Phase.TWO -> scrollY / collapsingDistance
            else -> 1f
        }
        val ivImageScale0 = 1.1f
        val ivImageScale1 = 1.0f
        val ivImageScale = (ivImageScale0 - ivImageScale1) * (1f - progress) + ivImageScale1
        binding.ivImage.scaleX = ivImageScale
        binding.ivImage.scaleY = ivImageScale
        binding.ivImage.alpha = 1f - progress
        binding.vToolbarBg.translationY = -collapsingDistance * progress
        binding.clImageContainer.translationY = binding.vToolbarBg.translationY
        binding.ivImage.translationY = -binding.clImageContainer.translationY / 2
    }

    // Handle motion (3)
    private fun renderIconBackground(scrollY: Float, collapsingDistance: Float) {
        val bgProgress = when(currentPhase) {
            Phase.EXPANDED -> 0f
            Phase.ONE, Phase.TWO -> scrollY / collapsingDistance
            else -> 1f
        }
        val iconBgColor =
            argbEvaluator.evaluate(bgProgress, expandedIconBgColor, collapsedIconBgColor) as Int
        binding.leftIconWidget.backgroundCircleColor = iconBgColor
        binding.rightIconWidget1.backgroundCircleColor = iconBgColor
        binding.rightIconWidget2.backgroundCircleColor = iconBgColor
    }

    // Handle motion (4) and (5)
    private fun renderMoreInfoIconSize(scrollY: Float, collapsingDistance: Float) {
        val progress = when(currentPhase) {
            Phase.EXPANDED, Phase.ONE -> 1f
            Phase.TWO -> 1 - (scrollY - collapsingDistance / 2) / (collapsingDistance / 2)
            else -> 0f
        }
        binding.rightIconWidget1.alpha = progress
        binding.rightIconWidget1.size = (binding.rightIconWidget2.size * progress).toInt()
    }

    // Handle motion (6)
    private fun renderSearchIconPosition(scrollY: Float, collapsingDistance: Float) {
        val progress = when(currentPhase) {
            Phase.EXPANDED, Phase.ONE -> 1f
            Phase.TWO -> 1 - (scrollY - collapsingDistance / 2) / (collapsingDistance / 2)
            else -> 0f
        }
        val marginEnd0 = context.getDimen(R.dimen.u8)
        val marginEnd1 = context.getDimen(R.dimen.u2)
        val marginEnd = (marginEnd0 - marginEnd1) * progress + marginEnd1
        binding.rightIconWidget2.updateLayoutParams<LayoutParams> {
            updateMarginsRelative(end = marginEnd.toInt())
        }
    }

    // Handle motion (7)
    private fun renderToolbarBackground(scrollY: Float, collapsingDistance: Float) {
        val alpha = when(currentPhase) {
            Phase.EXPANDED, Phase.ONE, Phase.TWO -> 0f
            Phase.THREE -> (scrollY - collapsingDistance) / context.getDimen(R.dimen.u3)
            else -> 1f
        }
        binding.flToolbarBgContainer.alpha = alpha
    }

    // Handle motion (8), (9) and (10)
    private fun renderTitles(scrollY: Float, collapsingDistance: Float) {
        val progress = when(currentPhase) {
            Phase.EXPANDED, Phase.ONE, Phase.TWO -> 0f
            Phase.THREE, Phase.FOUR -> (scrollY - collapsingDistance) / calcPhase3and4ScrollRange()
            Phase.COLLAPSED -> 1f
        }
        val translationY0 = context.getDimen(R.dimen.u1)
        val translationY = (1f - progress) * translationY0
        binding.tvTitle.translationY = translationY
        binding.tvTitle.alpha = progress
        bigTitle?.alpha = 1f - progress
    }

    enum class Phase { EXPANDED, ONE, TWO, THREE, FOUR, COLLAPSED }

}

