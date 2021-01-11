package la.me.leo.core_animation

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updatePadding
import androidx.core.widget.NestedScrollView

abstract class CollapsingWidget(context: Context, attrs: AttributeSet): ConstraintLayout(context, attrs) {

    protected lateinit var scrollView: NestedScrollView

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        scrollView.updatePadding(top = measuredHeight)
    }

    fun bind(scrollView: NestedScrollView) {
        scrollView.clipToPadding = false
        this.scrollView = scrollView
        scrollView.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            onScrollPositionChanged(scrollY.toFloat())
        }
    }

    protected abstract fun calcCollapsingDistance(): Float

    protected abstract fun onScrollPositionChanged(scrollY: Float)
}
