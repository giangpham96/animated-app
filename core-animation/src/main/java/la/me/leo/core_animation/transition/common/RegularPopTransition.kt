package la.me.leo.core_animation.transition.common

import android.animation.Animator
import android.view.View
import android.view.animation.AccelerateInterpolator
import la.me.leo.core_animation.animator.constructLifecycleAwareAnimator
import la.me.leo.core_animation.transition.FragmentExitTransition

class RegularPopTransition : FragmentExitTransition() {
    override fun createFragmentAnimator(fragmentRoot: View): Animator {
        fragmentRoot.alpha = 1f
        val x1 = -fragmentRoot.width.toFloat()
        return constructLifecycleAwareAnimator(300, AccelerateInterpolator(),
            onUpdate = { value ->
                fragmentRoot.alpha = 1 - value
                fragmentRoot.x =  x1 * value
            },
            lifecycle = fragment.lifecycle
        )
    }
}
