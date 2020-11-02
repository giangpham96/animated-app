package la.me.leo.core.base.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import la.me.leo.core.R

open class TranslucentIconImageView(
        context: Context,
        attrs: AttributeSet
) : AppCompatImageView(context, attrs) {

    var iconAlpha: Float = 1f
        set(value) {
            field = value
            invalidate()
        }

    init {
        val ta = getContext().obtainStyledAttributes(
                attrs,
                R.styleable.TranslucentIconImageView,
                0, 0
        )
        iconAlpha = ta.getFloat(R.styleable.TranslucentIconImageView_iconAlpha, 1f)
        ta.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        val drawable = this.drawable ?: return
        val oldAlpha = drawable.alpha
        val drawableAlpha = (255 * iconAlpha).toInt()
        if (drawable.alpha != drawableAlpha) {
            drawable.alpha = drawableAlpha
        }
        super.onDraw(canvas)
        drawable.alpha = oldAlpha
    }

}
