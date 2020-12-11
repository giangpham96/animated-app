package la.me.leo.core_animation.transition

import android.animation.Animator
import android.view.ViewGroup
import androidx.core.animation.addListener
import androidx.fragment.app.Fragment
import androidx.transition.TransitionValues

abstract class FragmentEnterTransition : FragmentTransition() {
    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {
        val animator = super.createAnimator(sceneRoot, startValues, endValues) ?: return null
        animator.addListener(
            onStart = { fragment.view?.bringToFront() },
            onEnd = { fragment.enterTransition = null }
        )
        return animator
    }

    override fun integrateWithFragment(fragment: Fragment) {
        this.fragment = fragment
        fragment.enterTransition = this
    }
}
