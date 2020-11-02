package la.me.leo.core.base.view

import android.content.Context
import android.util.AttributeSet
import la.me.leo.core.R
import java.lang.IllegalArgumentException

class XsRatingIconWidget(
        context: Context,
        attrs: AttributeSet
) : TranslucentIconImageView(context, attrs) {

    fun setIcon(rating5: Int, rating10: Float) {
        val colored = rating10 >= 9
        val iconId = when (rating5) {
            0 -> R.drawable.ic_xs_smiley_angry
            1 -> R.drawable.ic_xs_smiley_sad
            2 -> R.drawable.ic_xs_smiley_speechless
            3 -> R.drawable.ic_xs_smiley_quite_happy
            4 -> {
                if (colored) {
                    R.drawable.ic_xs_color_smiley_happy
                } else {
                    R.drawable.ic_xs_smiley_happy
                }
            }
            else -> throw IllegalArgumentException()
        }
        iconAlpha = if (colored) 1f else 0.55f
        setImageResource(iconId)
    }

}
