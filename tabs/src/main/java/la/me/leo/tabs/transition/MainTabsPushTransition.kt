package la.me.leo.tabs.transition

import android.animation.Animator
import android.animation.AnimatorSet
import android.view.View
import android.view.ViewAnimationUtils
import androidx.core.view.animation.PathInterpolatorCompat
import la.me.leo.core_animation.animator.cancelAnimatorOnDestroy
import la.me.leo.core_animation.animator.constructAnimator
import la.me.leo.core_animation.transition.FragmentEnterTransition
import la.me.leo.tabs.R
import kotlin.math.pow
import kotlin.math.sqrt

internal class MainTabsPushTransition(private val x: Int, private val y: Int) :
    FragmentEnterTransition() {

    override fun createFragmentAnimator(fragmentRoot: View): Animator {
        val animator1 = createCircularRevealAnimator(fragmentRoot)
        val animator2 = createTranslationAnimator(fragmentRoot)
        val animatorSet = AnimatorSet()
        animatorSet.playTogether(animator1, animator2)
        animatorSet.interpolator = PathInterpolatorCompat.create(0.25f, 0.1f, 0.25f, 1f)
        fragment.lifecycle.cancelAnimatorOnDestroy(animatorSet)
        return animatorSet
    }

    private fun createCircularRevealAnimator(fragmentRoot: View): Animator {
        val radius0 = 0f
        val radius1 = sqrt(fragmentRoot.height.toFloat().pow(2f) + x.toFloat().pow(2f))
        return ViewAnimationUtils.createCircularReveal(fragmentRoot, x, y, radius0, radius1)
            .setDuration(300L)
    }

    private fun createTranslationAnimator(fragmentRoot: View): Animator {
        val translationY0 = fragmentRoot.context.resources.getDimensionPixelSize(R.dimen.u2)
        return constructAnimator(
            duration = 300,
            onUpdate = { animatedValue ->
                with(fragmentRoot) {
                    translationX = (1 - animatedValue) * x
                    translationY = (1 - animatedValue) * translationY0
                }
            }
        )
    }
}
