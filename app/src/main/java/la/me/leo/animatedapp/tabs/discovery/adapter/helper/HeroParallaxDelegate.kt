package la.me.leo.animatedapp.tabs.discovery.adapter.helper

import android.view.View
import android.view.animation.AccelerateInterpolator
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.marginStart
import androidx.recyclerview.widget.RecyclerView
import la.me.leo.animatedapp.tabs.discovery.adapter.view_holder.HeroViewHolder
import kotlin.math.abs

internal class HeroParallaxDelegate(
    private val holder: HeroViewHolder,
    private val tvLeftText1: TextView,
    private val tvLeftText2: TextView,
    private val tvLeftText3: TextView,
    private val llRightTextsContainer: LinearLayout,
    private val vFade: View,
    private val recyclerView: RecyclerView
) {

    init {
        setup()
    }

    private fun setup() {
        val itemView = holder.itemView
        val x0 = recyclerView.paddingStart + itemView.marginStart
        val fadeAlphaInterpolator = AccelerateInterpolator()
        val scrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                var offset = abs(x0 - itemView.x) / recyclerView.width
                offset = offset.coerceIn(0f, 1f)
                vFade.alpha = fadeAlphaInterpolator.getInterpolation(offset)
                llRightTextsContainer.translationY = offset * llRightTextsContainer.height
                tvLeftText1.translationX = (itemView.x - x0) * 0.4f
                tvLeftText2.translationX = (itemView.x - x0) * 0.5f
                tvLeftText3.translationX = (itemView.x - x0) * 0.6f
            }
        }
        itemView.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {

            override fun onViewAttachedToWindow(v: View) {
                recyclerView.addOnScrollListener(scrollListener)
            }

            override fun onViewDetachedFromWindow(v: View) {
                recyclerView.removeOnScrollListener(scrollListener)
            }

        })
    }

}
