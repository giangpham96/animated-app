package la.me.leo.core.base.view

import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat.getColor
import la.me.leo.core.R
import kotlin.math.abs

class PageIndicatorWidget(
        context: Context,
        attrs: AttributeSet
) : View(context, attrs) {

    companion object {
        private const val GAP_FACTOR = 1
    }

    private var circleCount: Int = 0
    private var page: Int = 0
    private var offset: Float = 0f
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var activeColor: Int = getColor(context, R.color.dark_55p)
    private var nonActiveColor: Int = getColor(context, R.color.dark_12p)
    private var circleSize: Int = context.resources.getDimensionPixelSize(R.dimen.u0_5)
    private val argbEvaluator = ArgbEvaluator()

    init {
        readAttrs(attrs)
    }

    fun setPageCount(count: Int) {
        circleCount = count
        requestLayout()
    }

    fun onPageScrolled(position: Int, positionOffset: Float) {
        page = position
        offset = abs(positionOffset)
        invalidate()
    }

    private fun readAttrs(attrs: AttributeSet) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.PageIndicatorWidget)
        activeColor = a.getColor(R.styleable.PageIndicatorWidget_activeColor, activeColor)
        nonActiveColor = a.getColor(R.styleable.PageIndicatorWidget_nonActiveColor, nonActiveColor)
        circleSize = a.getDimensionPixelSize(R.styleable.PageIndicatorWidget_circleSize, circleSize)
        a.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val gap = circleSize * GAP_FACTOR
        val w = (circleSize + gap) * circleCount - gap
        setMeasuredDimension(w, circleSize)
    }

    override fun onDraw(canvas: Canvas) {
        val r = circleSize / 2f
        val gap = circleSize * GAP_FACTOR
        val nextPage = (page + 1) % circleCount
        for (i in 0 until circleCount) {
            paint.color = when (i) {
                page -> argbEvaluator.evaluate(1 - offset, nonActiveColor, activeColor) as Int
                nextPage -> argbEvaluator.evaluate(offset, nonActiveColor, activeColor) as Int
                else -> nonActiveColor
            }
            val x = r + i * (2 * r + gap)
            canvas.drawCircle(x, r, r, paint)
        }
    }

}
