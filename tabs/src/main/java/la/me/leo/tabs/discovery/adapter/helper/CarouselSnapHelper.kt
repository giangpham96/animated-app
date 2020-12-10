package la.me.leo.tabs.discovery.adapter.helper

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import la.me.leo.core.base.utils.endMargin
import la.me.leo.core.base.utils.startMargin
import la.me.leo.tabs.R
import kotlin.math.abs

internal class CarouselSnapHelper(context: Context) : LinearSnapHelper() {

    private val snappingMargin = context.resources.getDimensionPixelSize(R.dimen.u2)

    override fun findSnapView(layoutManager: RecyclerView.LayoutManager): View? {
        var leftChild: View? = null
        var rightChild: View? = null
        var minLeftDistance = Integer.MAX_VALUE
        var minRightDistance = Integer.MAX_VALUE
        for (i in 0 until layoutManager.childCount) {
            val child = layoutManager.getChildAt(i)!!

            val leftGap = if (layoutManager.getPosition(child) == 0) {
                child.startMargin() + layoutManager.paddingStart
            } else {
                2 * child.startMargin()
            }
            val leftDistance = abs(leftGap - child.left)
            if (leftDistance < minLeftDistance) {
                minLeftDistance = leftDistance
                leftChild = child
            }

            val rightGap = child.endMargin() + layoutManager.paddingEnd
            val rightDistance = abs(layoutManager.width - rightGap - child.right)
            if (rightDistance < minRightDistance) {
                minRightDistance = rightDistance
                rightChild = child
            }
        }
        return if (rightChild != null && layoutManager.getPosition(rightChild) == layoutManager.itemCount - 1 &&
                minRightDistance < minLeftDistance) {
            rightChild
        } else {
            leftChild
        }
    }

    override fun calculateDistanceToFinalSnap(layoutManager: RecyclerView.LayoutManager, targetView: View): IntArray? {
        val distance = when (layoutManager.getPosition(targetView)) {
            0 -> targetView.left - targetView.startMargin() - layoutManager.paddingStart
            layoutManager.itemCount - 1 -> {
                targetView.right + targetView.endMargin() + layoutManager.paddingEnd - layoutManager.width
            }
            else -> targetView.left - snappingMargin
        }
        return intArrayOf(distance, 0)
    }

}
