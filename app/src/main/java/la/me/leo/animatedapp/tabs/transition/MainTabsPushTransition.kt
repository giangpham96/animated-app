package la.me.leo.animatedapp.tabs.transition

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewAnimationUtils
import androidx.core.view.animation.PathInterpolatorCompat
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
        val animator1 = ViewAnimationUtils.createCircularReveal(fragmentRoot, x, y, radius0, radius1)
        val animator2 = ValueAnimator.ofFloat(0f, 1f)
        animator2.addUpdateListener {
            val animatedValue = it.animatedValue as Float
            with(fragmentRoot) {
                translationX = (1 - animatedValue) * x
                translationY = (1 - animatedValue) * translationY0
            }
        }
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(animator1, animator2)
        animatorSet.interpolator = PathInterpolatorCompat.create(0.25f, 0.1f, 0.25f, 1f)
        animatorSet.duration = 300L
        return animatorSet
    }
}
