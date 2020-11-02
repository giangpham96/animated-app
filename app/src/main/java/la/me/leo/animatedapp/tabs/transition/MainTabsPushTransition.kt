package la.me.leo.animatedapp.tabs.transition

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewGroup
import androidx.core.view.animation.PathInterpolatorCompat
import androidx.core.view.children
import androidx.core.view.forEach
import androidx.recyclerview.widget.RecyclerView
import la.me.leo.animatedapp.R
import la.me.leo.core.base.transition.FragmentTransition
import kotlin.math.pow
import kotlin.math.sqrt

internal class MainTabsPushTransition(private val x: Int, private val y: Int) :
    FragmentTransition(isEnterTransition = true) {

    override fun createFragmentAnimator(fragmentRoot: View): Animator {
        val translationY0 = fragmentRoot.context.resources.getDimensionPixelSize(R.dimen.u2)
        val radius0 = 0f
        val radius1 = sqrt(fragmentRoot.height.toFloat().pow(2f) + x.toFloat().pow(2f))
        val animator1 =
            ViewAnimationUtils.createCircularReveal(fragmentRoot, x, y, radius0, radius1)
        val animator2 = ValueAnimator.ofFloat(0f, 1f)
        animator2.addUpdateListener {
            val animatedValue = it.animatedValue as Float
            with(fragmentRoot) {
                translationX = (1 - animatedValue) * x
                translationY = (1 - animatedValue) * translationY0
            }
        }

        val recyclerView =
            (fragmentRoot as? ViewGroup)?.children?.firstOrNull { it is RecyclerView }
        val recyclerViewItemAnimators = if (recyclerView is RecyclerView) {
            val baseTranslationY0 = fragmentRoot.context.resources.getDimensionPixelSize(R.dimen.u1)
            recyclerView.forEach { it.alpha = 0f }
            recyclerView.children.mapIndexed { i, v ->
                val Y0 = baseTranslationY0 + i * baseTranslationY0 * 2
                ValueAnimator.ofFloat(0f, 1f).apply {
                    addUpdateListener {
                        val animatedValue = it.animatedValue as Float
                        v.translationY = Y0 * (1 - animatedValue)
                        v.alpha = 0.5f + 0.5f * animatedValue
                    }
                    startDelay = 50L * i
                }
            }.toList()
        } else {
            emptyList()
        }

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(recyclerViewItemAnimators + listOf(animator1, animator2))
        animatorSet.interpolator = PathInterpolatorCompat.create(0.25f, 0.1f, 0.25f, 1f)
        animatorSet.duration = 300L
        return animatorSet
    }
}
