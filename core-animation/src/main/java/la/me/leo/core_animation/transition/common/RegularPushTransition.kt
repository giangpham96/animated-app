package la.me.leo.core_animation.transition.common

import android.animation.Animator
import android.view.View
import android.view.animation.DecelerateInterpolator
import la.me.leo.core_animation.animator.constructLifecycleAwareAnimator
import la.me.leo.core_animation.transition.FragmentEnterTransition

class RegularPushTransition : FragmentEnterTransition() {
    override fun createFragmentAnimator(fragmentRoot: View): Animator {
        fragmentRoot.alpha = 0f
        val x0 = fragmentRoot.width.toFloat()
        return constructLifecycleAwareAnimator(300, DecelerateInterpolator(),
            onUpdate = { value ->
                fragmentRoot.alpha = value
                fragmentRoot.x = x0 * (1 - value)
            },
            lifecycle = fragment.lifecycle
        )
    }
}
