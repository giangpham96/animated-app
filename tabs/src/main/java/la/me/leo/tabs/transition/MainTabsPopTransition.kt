package la.me.leo.tabs.transition

import android.animation.Animator
import android.view.View
import la.me.leo.core_animation.animator.cancelAnimatorOnDestroy
import la.me.leo.core_animation.animator.constructAnimator
import la.me.leo.core_animation.transition.FragmentExitTransition

internal class MainTabsPopTransition : FragmentExitTransition() {

    override fun createFragmentAnimator(fragmentRoot: View): Animator {
        val animator = constructAnimator(
            duration = 300,
            onUpdate = { fragmentRoot.alpha = 1f - it },
            onEnd = { fragmentRoot.alpha = 1f }
        )
        fragment.lifecycle.cancelAnimatorOnDestroy(animator)
        return animator
    }
}
