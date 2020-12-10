package la.me.leo.tabs.transition

import android.animation.Animator
import android.view.View
import androidx.core.view.isVisible
import la.me.leo.core_animation.animator.constructLifecycleAwareAnimator
import la.me.leo.core_animation.transition.FragmentTransition.FragmentExitTransition

internal class MainTabsPopTransition : FragmentExitTransition() {

    override fun createFragmentAnimator(fragmentRoot: View): Animator {
        val animator = constructLifecycleAwareAnimator(
            duration = 300,
            onUpdate = { fragmentRoot.alpha = 1f - it },
            onStart = { fragmentRoot.isVisible = true },
            onEnd = {
                fragmentRoot.isVisible = false
                fragmentRoot.alpha = 1f
            },
            lifecycle = fragment.lifecycle
        )
        return animator
    }
}
