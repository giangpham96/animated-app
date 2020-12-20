package la.me.leo.core_animation.transition

import android.animation.Animator
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.core.animation.addListener
import androidx.fragment.app.Fragment
import androidx.transition.TransitionValues

abstract class FragmentEnterTransition : FragmentTransition() {
    override fun createAnimator(sceneRoot: ViewGroup, startValues: TransitionValues?,
        endValues: TransitionValues?): Animator? {
        val animator = super.createAnimator(sceneRoot, startValues, endValues) ?: return null
        animator.addListener(
            onStart = { fragment.view?.bringToFront() },
            onEnd = { fragment.enterTransition = null }
        )
        return animator
    }

    @CallSuper override fun integrateWithFragment(fragment: Fragment) {
        super.integrateWithFragment(fragment)
        fragment.enterTransition = this
    }
}
