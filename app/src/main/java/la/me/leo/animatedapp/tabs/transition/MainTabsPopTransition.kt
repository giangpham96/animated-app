package la.me.leo.animatedapp.tabs.transition

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.View
import androidx.core.animation.addListener
import androidx.core.view.isVisible
import la.me.leo.core.base.transition.FragmentTransition

internal class MainTabsPopTransition : FragmentTransition(isEnterTransition = false) {

    override fun createFragmentAnimator(
        fragmentRoot: View
    ): Animator {
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.addUpdateListener {
            val animatedValue = it.animatedValue as Float
            fragmentRoot.alpha = 1f - animatedValue
        }
        animator.addListener(
            onStart = { fragmentRoot.isVisible = true },
            onEnd = {
                fragmentRoot.isVisible = false
                fragmentRoot.alpha = 1f
            }
        )
        animator.duration = 300L
        return animator
    }
}
