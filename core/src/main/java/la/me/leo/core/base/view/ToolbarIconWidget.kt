package la.me.leo.core.base.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMarginsRelative
import la.me.leo.core.R
import la.me.leo.core.base.utils.*

/**
 * Note: this widget controls own visibility
 */
class ToolbarIconWidget(
    context: Context,
    attrs: AttributeSet
) : AppCompatImageView(context, attrs) {

    var backgroundCircleColor: Int
        set(value) {
            paint.color = value
            invalidate()
        }
        get() = paint.color
    var ignoreStatusBar: Boolean = false
        set(value) {
            field = value
            if (isAttachedToWindow) {
                setupMargins()
            }
        }
    var size: Int = context.getDimen(R.dimen.u5)
        set(value) {
            field = value
            val newPadding = field / 4
            setPadding(newPadding, newPadding, newPadding, newPadding)
            setupMargins()
            requestLayout()
        }
    var overrideTopMargin: Boolean = true
        set(value) {
            field = value
            if (isAttachedToWindow) {
                setupMargins()
            }
        }
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        val p = context.getDimen(R.dimen.u1_25)
        setPadding(p, p, p, p)
        backgroundCircleColor = ContextCompat.getColor(context, R.color.pepper_8)
        isVisible = false
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setupMargins()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val spec = MeasureSpec.makeMeasureSpec(size, MeasureSpec.EXACTLY)
        super.onMeasure(spec, spec)
    }

    override fun onDraw(canvas: Canvas) {
        if (drawable != null) {
            canvas.drawCircle(width / 2f, height / 2f, width / 2f, paint)
        }
        super.onDraw(canvas)
    }

    fun setIcon(icon: Int?, clickListener: (() -> Unit)?) {
        if (icon != null) {
            isVisible = true
            setImageResource(icon)
        } else {
            isVisible = false
        }
        setOnClickListener { clickListener?.invoke() }
        foreground = clickListener?.let { ContextCompat.getDrawable(context, R.drawable.fg_light_circle_btn) }
    }

    private fun setupMargins() {
        val top = if (overrideTopMargin) {
            (context.toolbarHeight - size) / 2 + if (ignoreStatusBar) 0 else context.statusBarHeight
        } else {
            (layoutParams as ViewGroup.MarginLayoutParams).topMargin
        }
        updateLayoutParams<ConstraintLayout.LayoutParams> {
            updateMarginsRelative(
                top = top,
                start = startMargin().takeIf { it > 0 } ?: context.getDimen(R.dimen.u2),
                end = endMargin().takeIf { it > 0 } ?: context.getDimen(R.dimen.u2)
            )
        }
    }

}
